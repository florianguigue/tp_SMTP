package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
}
