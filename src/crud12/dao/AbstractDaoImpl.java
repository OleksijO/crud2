package crud12.dao;

import crud10.Constants;
import crud12.entities.Category;
import crud12.entities.DomainObject;
import crud12.entities.Product;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.Collection;

import static crud12.dao.SessionFactoryImpl.getSession;

public abstract class AbstractDaoImpl<T extends DomainObject> implements Dao<T, Integer> {
    protected int productCount = -1;
    protected int categoryCount = -1;




    @Override
    public Integer create(T newInstance) {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            session.save(newInstance);
            session.getTransaction().commit();

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return newInstance.getId();
    }

    @Override
    public void update(T transientObject) {
        Session session = null;
        if (transientObject.getId() == null) throw new IllegalArgumentException("Id cannot be NULL while update!");
        try {
            session = getSession();
            session.beginTransaction();
            session.update(transientObject);
            session.getTransaction().commit();

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public int getTotalCount(Class entityClass){
        if (entityClass.equals(Category.class)) {
            return categoryCount;
        } else if ((entityClass.equals(Product.class))) {
            return productCount;
        }
        throw new IllegalArgumentException("No such class " + entityClass.getSimpleName() + " extending DomainObject");
    }

    protected void updateCounts(Session session) {
        session.beginTransaction();
        productCount = ((BigInteger) session.createSQLQuery("SELECT COUNT(*) FROM product;").uniqueResult()).intValue();
        categoryCount = ((BigInteger) session.createSQLQuery("SELECT COUNT(*) FROM category;").uniqueResult()).intValue();
        session.getTransaction().commit();
    }

    @Override
    public void createList(Collection list) {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            int count = 0;
            for (Object newInstance : list) {
                session.save(newInstance);
                if (++count % 25 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            session.getTransaction().commit();
            updateCounts(session);
           } finally {
            if (session != null) {
                session.close();
            }

        }
    }

    @Override
    public void recreateTables() {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
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
            session.createSQLQuery("INSERT INTO category (id, parentid, name,description) VALUES (0, NULL ,'" + Constants.ROOT_CATEGORY_NAME + "','');").executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
