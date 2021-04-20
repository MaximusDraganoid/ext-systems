package edu.javacourse.register.manager;

import edu.javacourse.register.domain.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;
import java.io.Serializable;
import java.util.List;

public class PersonManager {
    public static void main(String[] args) {

        sessionHibernateExample();

        jpaExample();

    }

    private static void jpaExample() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");

        EntityManager em = emf.createEntityManager(); // аналог session в hibernate

        em.getTransaction().begin();
        Person p = new Person();
        p.setLastName("Алексей");
        p.setFirstName("Федоров");

        em.persist(p);
        System.out.println(p.getPersonId());
        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        List from_person = em.createQuery("FROM Person").getResultList();
        from_person.stream().forEach(System.out::println);

        em.close();
    }

    private static void sessionHibernateExample() {
        System.out.println();
        System.out.println();
        SessionFactory sf = buildSessionFactory();
        //получаем нам сессию для взаимодействия с бд
        Session session = sf.openSession();
        //после этого создаем транзакцию
        session.getTransaction().begin();

        Person p = new Person();
        p.setFirstName("Василий");
        p.setLastName("Сидоров");

        Serializable save = session.save(p); // поулчаем идентификатор созданной в бд записи
        Long id = (Long) save;
        System.out.println(id);

        session.getTransaction().commit();
        session.close();

        //в этом случае мы не открывали транзакцию, т.к. мы просто считывали данные из таблички
        session = sf.openSession();
        Person person = session.get(Person.class, id);
        System.out.println(person);
        session.close();

        session = sf.openSession();
        //создаем hql запрос и через него вытаскиваем данные
        List<Person> from_person = session.createQuery("FROM Person", Person.class).list();
        from_person.stream().forEach(System.out::println);
        session.close();
    }

    //генерация фактически коннектов к бд (повзоляет получать аналог коннектов к бд)
    private static SessionFactory buildSessionFactory() {
        try {
            //сервис, который регистрируется в hibernate и через который происходит управление объектами
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
