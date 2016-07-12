package crud11.dao;

import crud10.Constants;
import crud11.entity.Category;
import crud11.entity.Product;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.List;

import static crud11.dao.SessionFactoryImpl.*;

public class CategoryDaoImpl implements Dao<Category, Integer> {

    @Override
    public Integer create(Category newInstance) {
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
    public Category read(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = null;
        Category category = null;
        try {
            session = getSession();
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
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return category;
    }

    @Override
    public void update(Category transientObject) {
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
