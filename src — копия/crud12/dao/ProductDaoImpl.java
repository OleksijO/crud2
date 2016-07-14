package crud12.dao;

import crud12.entities.Category;
import crud12.entities.Product;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ProductDaoImpl extends AbstractDaoImpl<Product> {

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void update(Product transientObject) {
        Session session = null;
        if (transientObject.getId() == null) throw new IllegalArgumentException("Id cannot be NULL while update!");
        try {

            session = getSession();
            Product detachedObject=(Product) session.load(Product.class,transientObject.getId());
            //session.beginTransaction();
            copyData(transientObject,detachedObject);
            detachedObject.setPrice(transientObject.getPrice());
            detachedObject.setQuantity(transientObject.getQuantity());
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
    public void delete(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        Session session = null;
        Product Product;
        try {
            session = getSession();
            Product = (Product) session.load(Product.class, id);
            session.beginTransaction();
            session.delete(Product);
            session.getTransaction().commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
