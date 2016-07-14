package crud14.dao;

import crud10.Constants;
import crud14.entities.Category;
import crud14.entities.Product;
import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CategoryDaoImpl extends AbstractDaoImpl<Category> {


    @Override
    @Transactional(readOnly = true)
    public Category read(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = getSession();
        Category category = (Category) session.get(Category.class, id);
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
        updateCounts();
        return category;
    }

    @Override
    @Transactional
    public void update(Category transientObject) {
        if (transientObject.getId() == null) throw new IllegalArgumentException("Id cannot be NULL while update!");
        Session session = getSession();
        Category detachedObject = (Category) session.load(transientObject.getClass(), transientObject.getId());
        copyFromTransientToDetached(transientObject, detachedObject);
        session.update(detachedObject);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if ((id == null) || (id == Constants.ROOT_CATEGORY_ID)) throw new IllegalArgumentException();
        Session session = getSession();
        Category category = (Category) session.load(Category.class, id);
        session.delete(category);
    }
}
