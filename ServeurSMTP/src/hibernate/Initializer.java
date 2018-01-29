package hibernate;

import model.Authentication;
import model.Mail;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;

public class Initializer {
    private final String hibernateConfPath = "conf/hibernate.cfg.xml";

    private SessionFactory sessionFactory;

    public Initializer() {
        init();
    }

    private void init() {
        String filePath = new File(hibernateConfPath).getAbsolutePath();
        File file = new File(filePath);
        Configuration cfg = new Configuration().configure(file);

        cfg.addAnnotatedClass(Authentication.class);
        cfg.addAnnotatedClass(Mail.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties()).build();

        sessionFactory = cfg.buildSessionFactory(serviceRegistry);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
