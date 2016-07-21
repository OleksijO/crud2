package crud12.dao;

import crud12.entities.Category;
import crud12.entities.Product;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

public class ProductDaoImpl extends AbstractDaoImpl<Product> {

    @Override
    public Product read(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = null;
        Product Product = null;
        try {
            session = getSession();
            Product = (Product) session.get(Product.class, id);
            if (Product == null) throw new ObjectNotFoundException(id, "Product");

        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Product;
    }

    @Override
    @Transactional
    public void update(Product transientObject) {
        if (transientObject.getId() == null) throw new IllegalArgumentException("Id cannot be NULL while update!");
        Session session = getSession();
        Product detachedObject=(Product) session.load(transientObject.getClass(), transientObject.getId());
        copyFromTransientToDetached(transientObject,detachedObject);
        session.update(detachedObject);
    }

    @Override
    public void delete(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = null;
        Product Product;
        try {
            session = getSession();
            Product = (Product) session.load(Product.class, id);

            session.delete(Product);

        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}