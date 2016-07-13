package crud12.dao;

import crud12.entities.Product;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

import static crud12.dao.SessionFactoryImpl.getSession;

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
