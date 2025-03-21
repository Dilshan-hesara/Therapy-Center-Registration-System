package lk.cw.config;

import lk.cw.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration() throws IOException {
//        Configuration configuration = new Configuration();
//

//
//        configuration.configure();


  try {
      Configuration configuration = new Configuration();
      Properties properties = new Properties();

      // Load properties
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
      configuration.setProperties(properties);

      configuration.addAnnotatedClass(User.class);

        configuration.addAnnotatedClass(Patient.class);
       configuration.addAnnotatedClass(Therapist.class);
       configuration.addAnnotatedClass(TherapyProgram.class);
       configuration.addAnnotatedClass(TherapySession.class);
       configuration.addAnnotatedClass(Payment.class);


//        configuration.addAnnotatedClass(User.class);
//                .addAnnotatedClass(Item.class)
//                .addAnnotatedClass(Order.class)
//                .addAnnotatedClass(OrderDetails.class);
      sessionFactory = configuration.buildSessionFactory();
  }


  catch (IOException e) {
        throw new IOException("Failed to load hibernate.properties", e);
    } catch (Exception e) {
        throw new RuntimeException("Failed to create SessionFactory", e);
    }
    }

    public static FactoryConfiguration getInstance() throws IOException {
        return (factoryConfiguration == null) ?
                factoryConfiguration = new FactoryConfiguration() :
                factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
