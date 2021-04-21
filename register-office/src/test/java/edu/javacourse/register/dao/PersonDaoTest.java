package edu.javacourse.register.dao;

import edu.javacourse.register.domain.Person;
import edu.javacourse.register.domain.PersonFemale;
import edu.javacourse.register.domain.PersonMale;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class PersonDaoTest {

    @Test
    public void findPersons() {
        PersonDao dao = new PersonDao();
        List<Person> persons = dao.findPersons();
        persons.stream().forEach(p -> {
            System.out.println("Person : " + p);
            System.out.println("Class name : " + p.getClass().getName());
            System.out.println("certificates : " + p.getCertificate());
//            if (p instanceof PersonMale) {
//                System.out.println("male birth certificate" + ((PersonMale) p).getBirthCertificates().size());
//                System.out.println("male marriage certificate" + ((PersonMale) p).getMarriageCertificates().size());
//            } else {
//                System.out.println("female birth certificate" + ((PersonFemale) p).getBirthCertificates().size());
//                System.out.println("female marriage certificate" + ((PersonFemale) p).getMarriageCertificates().size());
//            }
        });
    }


}