package crud19.service.dao;

import crud19.entities.secure.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public class UserDaoImpl implements UsersDao {
    protected SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    @Override
    public Integer create(User transientObject) {
        return null;
    }
        /* to create admin ->
        DROP TABLE IF EXISTS users;
        CREATE TABLE users(
        id SERIAL NOT NULL,
        login VARCHAR(16) NOT NULL,
        password VARCHAR(50) NOT NULL,
        name VARCHAR(30),
        PRIMARY KEY (login)
        );
        INSERT INTO users (login, password, name) VALUES ('admin', MD5('admin' || '1029384756'), 'Administrator');
        */
    @Override
    @SuppressWarnings("unchecked")
    public <K> K retrieve(K transientObject) {
        User trans = (User) transientObject;
        return (K) getSession().get(trans.getClass(), trans.getLogin());
    }

    @Override
    public void update(User transientObject) {

    }

    @Override
    public void delete(User transientObject) {

    }

    @Override
    public int getTotalCount(Class entityClass) {
        return 0;
    }

    @Override
    public void createList(Collection list) {

    }

    @Override
    public void recreateTables() {

    }
}
