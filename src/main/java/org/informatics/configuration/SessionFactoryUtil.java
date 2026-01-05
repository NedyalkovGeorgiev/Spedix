package org.informatics.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.informatics.entity.Bus;
import org.informatics.entity.CargoTransport;
import org.informatics.entity.Client;
import org.informatics.entity.Company;
import org.informatics.entity.ConcreteMixer;
import org.informatics.entity.Driver;
import org.informatics.entity.Employee;
import org.informatics.entity.PassengerTransport;
import org.informatics.entity.RefrigeratedTruck;
import org.informatics.entity.Staff;
import org.informatics.entity.Tanker;
import org.informatics.entity.Transport;
import org.informatics.entity.Truck;
import org.informatics.entity.Vehicle;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Company.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Vehicle.class);
            configuration.addAnnotatedClass(Transport.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Bus.class);
            configuration.addAnnotatedClass(Truck.class);
            configuration.addAnnotatedClass(Driver.class);
            configuration.addAnnotatedClass(PassengerTransport.class);
            configuration.addAnnotatedClass(CargoTransport.class);
            configuration.addAnnotatedClass(Tanker.class);
            configuration.addAnnotatedClass(RefrigeratedTruck.class);
            configuration.addAnnotatedClass(ConcreteMixer.class);
            configuration.addAnnotatedClass(Staff.class);
            ServiceRegistry serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}