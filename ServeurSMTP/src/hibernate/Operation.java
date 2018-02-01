package hibernate;

import model.Authentication;
import model.Mail;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class Operation {

    Initializer initializer;
    Session session;

    public Operation() {
        initializer = new Initializer();
    }

    /**
     * @param o
     */
    public void insert(Object o) {
        session = initializer.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * @param id
     * @return
     */
    public Authentication getAuthentication(Integer id) {
        try {
            session = initializer.getSessionFactory().openSession();
            return session.load(Authentication.class, id);
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        } finally {
            if (session.isOpen())
                session.close();
        }
        return null;
    }

    /**
     * @param login
     * @return
     */
    public Authentication getAuthentication(String login) {
        try {
            session = initializer.getSessionFactory().openSession();
            String hql = "FROM Authentication a WHERE a.login = :userName";
            Query query = session.createQuery(hql).setParameter("userName", login);
            return (Authentication) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        } finally {
            if (session.isOpen())
                session.close();
        }
        return null;
    }

    /**
     * @param user
     * @return
     */
    public Long getMessageNumber(Authentication user) {
        try {
            session = new Initializer().getSessionFactory().openSession();
            String hql = "SELECT count(*) FROM Mail m WHERE m.addressReceiver = :addressReceiver";
            Query query = session.createQuery(hql).setParameter("addressReceiver", user.getAddress());
            return (Long) query.getSingleResult();
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        } finally {
            session.close();
        }
        return null;
    }

    public Integer getTailleMessages(Authentication user) {
        try {
            int tailleDepot = 0;
            session = initializer.getSessionFactory().openSession();
            String hql = "SELECT m.body FROM Mail m WHERE m.addressReceiver = :addressReceiver";
            Query query = session.createQuery(hql).setParameter("addressReceiver", user.getAddress());
            List<String> mails = query.getResultList();
            for (String mail : mails) {
                tailleDepot += mail.getBytes().length;
            }
            return tailleDepot;
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
        } finally {
            session.close();
        }
        return null;
    }

    public String getMessage(Integer nbMessage, Authentication user) {
        try {
            session = initializer.getSessionFactory().openSession();
            String hql = "FROM Mail m WHERE m.id = :idMessage";
            Query query = session.createQuery(hql).setParameter("idMessage", nbMessage);
            Mail mail = (Mail) query.getSingleResult();
            String returnString = "To:" + user.getAddress() + "\r\n"
                    + "Subject:" + mail.getSubject() + "\r\n"
                    + "Date:" + mail.getDate() + "\r\n"
                    + "From: " + mail.getAddressSender()+ "\r\n"
                    + mail.getBody() + "\r\n"
                    + ".";
            return returnString;
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    public List<Mail> getUserMails(Authentication user) {
        try {
            session = initializer.getSessionFactory().openSession();
            String hql = "FROM Mail m WHERE m.addressReceiver = :addressReceiver";
            Query query = session.createQuery(hql).setParameter("addressReceiver", user.getAddress());
            return query.getResultList();
        } catch (Exception e) {
            System.err.println(e.getStackTrace().toString());
            return null;
        } finally {
            if (session.isOpen())
                session.close();
        }
    }
}


