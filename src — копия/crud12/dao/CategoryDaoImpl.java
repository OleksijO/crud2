package crud12.dao;

import crud10.Constants;
import crud12.entities.Category;
import crud12.entities.Product;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CategoryDaoImpl extends AbstractDaoImpl<Category>  {


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void update(Category transientObject) {
        Session session = null;
        if (transientObject.getId() == null) throw new IllegalArgumentException("Id cannot be NULL while update!");
        try {

            session = getSession();
            Category detachedObject=(Category) session.load(Category.class,transientObject.getId());
            //session.beginTransaction();
            copyData(transientObject,detachedObject);
            session.update(detachedObject);
            //session.getTransaction().commit();
            System.out.println("update "+transientObject.getClass().getSimpleName()+" id="+transientObject.getId());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    @Override
    public Category read(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = null;
        Category category = null;
        try {
            session = getSession();
            session.beginTransaction();
            category = (Category) session.get(Category.class, id);
            if (category == null) throw new ObjectNotFoundException(id, "Category");
            Criteria categoryCriteria = session.createCriteria(Category.class);
            Criteria productCriteria = session.createCriteria(Product.class);
            productCriteria.add(Restrictions.eq("parent", category));
            categoryCriteria.add(Restrictions.eq("parent", category));
            @SuppressWarnings("unchecked")
            List<Category> listCategory = (List<Category>) categoryCriteria.addOrder(Order.desc("id")).list();
            @SuppressWarnings("unchecked")
            List<Product> listProduct = (List<Product>) productCriteria.addOrder(Order.desc("id")).list();
            category.setSubCategories(listCategory);
            category.setProducts(listProduct);

            session.getTransaction().commit();
            updateCounts(session);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return category;
    }

    @Override
    public void delete(Integer id) {
        if ((id == null) || (id == Constants.ROOT_CATEGORY_ID)) throw new IllegalArgumentException();
        Session session = null;
        Category category;
        try {
            session = getSession();
            category = (Category) session.load(Category.class, id);
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
