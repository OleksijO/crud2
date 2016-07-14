package crud14.dao;

import crud14.entities.Product;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

public class ProductDaoImpl extends AbstractDaoImpl<Product> {

    @Override
    @Transactional(readOnly = true)
    public Product read(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = getSession();
        Product product = (Product) session.get(Product.class, id);
        if (product == null) throw new ObjectNotFoundException(id, "Product");
        return product;
    }

    @Override
    @Transactional
    public void update(Product transientObject) {
        if (transientObject.getId() == null) throw new IllegalArgumentException("Id cannot be NULL while update!");
        Session session = getSession();
        Product detachedObject = (Product) session.load(transientObject.getClass(), transientObject.getId());
        copyFromTransientToDetached(transientObject, detachedObject);
        session.update(detachedObject);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = getSession();
        Product product = (Product) session.load(Product.class, id);
        session.delete(product);
    }
}
