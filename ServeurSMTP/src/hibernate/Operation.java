package hibernate;

import model.Authentication;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.naming.AuthenticationException;
import javax.persistence.Query;

public class Operation {

    Initializer initializer;
    Session session;

    public Operation() {
        initializer = new Initializer();
    }

    public void insert(Object o) {
        session = initializer.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }

    public Authentication getAuthentication(Integer id) {
        try {
            session = initializer.getSessionFactory().openSession();
            return session.load(Authentication.class, id);
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        } finally {
            session.close();
        }
        return null;
    }

    public Authentication getAuthentication(String login) {
        try {
            session = initializer.getSessionFactory().openSession();
            String hql = "from Authentication a where a.login = :userName";
            Query query = session.createQuery(hql).setParameter("userName", login);
            return (Authentication) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        } finally {
            session.close();
        }
        return null;
    }
}
