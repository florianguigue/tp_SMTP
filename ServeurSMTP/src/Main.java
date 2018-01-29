import hibernate.Operation;
import model.Authentication;
import model.Mail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Operation operation = new Operation();
        Authentication authentication = new Authentication("test", "test", "test");
        operation.insert(authentication);

    }
}
