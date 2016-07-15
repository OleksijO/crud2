package crud14.service.dao;

import crud10.Constants;

import crud14.entities.Category;
import crud14.entities.DomainObject;

import crud14.entities.Product;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class DomainDaoImpl implements DomainDao {
    protected int productCount = -1;
    protected int categoryCount = -1;
    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public Integer create(DomainObject newInstance) {
        Session session = getSession();
        session.save(newInstance);
        return newInstance.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public DomainObject retrieve(DomainObject transientObject) {
        if (transientObject.getId() == null) throw new IllegalArgumentException();
        Session session = getSession();
        DomainObject detach = (DomainObject) session.get(transientObject.getClass(), transientObject.getId());
        if (detach == null) throw new ObjectNotFoundException(transientObject.getId(), transientObject.getClass().getSimpleName());
        if (detach instanceof Category) {
            Category category = (Category) detach;
            Criteria categoryCriteria = session.createCriteria(Category.class);
            Criteria productCriteria = session.createCriteria(Product.class);
            productCriteria.add(Restrictions.eq("parent", detach));
            categoryCriteria.add(Restrictions.eq("parent", detach));
            @SuppressWarnings("unchecked")
            List<Category> listCategory = (List<Category>) categoryCriteria.addOrder(Order.desc("id")).list();
            @SuppressWarnings("unchecked")
            List<Product> listProduct = (List<Product>) productCriteria.addOrder(Order.desc("id")).list();
            category.setSubCategories(listCategory);
            category.setProducts(listProduct);
            detach = category;
        }
        updateCounts();
        return detach;
    }

    @Override
    @Transactional
    public void update(DomainObject transientObject) {
        if (transientObject.getId() == null) throw new IllegalArgumentException("Can't update item with id = NULL!");
        Session session = getSession();
        DomainObject detachedObject = (Category) session.load(transientObject.getClass(), transientObject.getId());
        copyFromTransientToDetached(transientObject, detachedObject);
        session.update(detachedObject);
    }

    @Override
    @Transactional
    public void delete(DomainObject transientObject) {
        if (transientObject.getId() == null) throw new IllegalArgumentException("Can't delete item with id = NULL!");
        Session session = getSession();
        Product product = (Product) session.load(transientObject.getClass(), transientObject.getId());
        session.delete(product);
    }

    @Override
    public int getTotalCount(Class entityClass) {
        if (entityClass.equals(Category.class)) {
            return categoryCount;
        } else if ((entityClass.equals(Product.class))) {
            return productCount;
        }
        throw new IllegalArgumentException("No such class " + entityClass.getSimpleName() + " extending DomainObject");
    }

    @Transactional(readOnly = true)
    protected void updateCounts() {
        Session session = getSession();
        productCount = ((BigInteger) session.createSQLQuery("SELECT COUNT(*) FROM product;").uniqueResult()).intValue();
        categoryCount = ((BigInteger) session.createSQLQuery("SELECT COUNT(*) FROM category;").uniqueResult()).intValue();
    }

    @Override
    @Transactional
    public void createList(Collection list) {
        Session session = getSession();
        int count = 0;
        for (Object newInstance : list) {
            session.save(newInstance);
            if (++count % 25 == 0) {
                session.flush();
                session.clear();
            }
        }
        updateCounts();
    }

    @Override
    @Transactional
    public void recreateTables() {
        Session session = getSession();
        session.createSQLQuery("DROP TABLE IF EXISTS product;").executeUpdate();
        session.createSQLQuery("DROP TABLE IF EXISTS category;").executeUpdate();
        session.createSQLQuery("CREATE TABLE category(" +
                "id SERIAL NOT NULL, " +
                "parentid INT, " +
                "name VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255), " +
                "PRIMARY KEY (id)," +
                "FOREIGN KEY (parentid) REFERENCES category(id)" +
                "ON DELETE CASCADE" +
                ");")
                .executeUpdate();
        session.createSQLQuery("CREATE TABLE product(" +
                "id SERIAL NOT NULL PRIMARY KEY, " +
                "parentid INT NOT NULL REFERENCES category(id) " +
                "ON DELETE CASCADE, " +
                "name VARCHAR(255) NOT NULL, " +
                "description VARCHAR(255), " +
                "price BIGINT, " +
                "quantity INT" +
                ");")
                .executeUpdate();
        session.createSQLQuery("" +
                "INSERT INTO category (id, parentid, name,description)" +
                " VALUES (" + Constants.ROOT_CATEGORY_ID + ", NULL ,'" +
                Constants.ROOT_CATEGORY_NAME + "','');")
                .executeUpdate();
    }

    protected void copyFromTransientToDetached(DomainObject trans, DomainObject detach) {
        detach.setName(trans.getName());
        detach.setDescription(trans.getDescription());
        if ((trans instanceof Product) && (detach instanceof Product)) {
            ((Product) detach).setPrice(((Product) trans).getPrice());
            ((Product) detach).setQuantity(((Product) trans).getQuantity());
        }
    }
}