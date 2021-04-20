package edu.javacourse.register.domain;

import javax.persistence.*;

//связываем класс с таблицей в базе
@Entity
//указываем имя таблицы, с которой связываем (по умолчанию будет искать таблицу с названием Person)
@Table (name = "person")
public class Person {
    //указываем, что поле будет нашим id
    @Id
    //выбираем как будет генерироваться id нашей создаваемой записи
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //описываем с каким столбцом связываем этот атрибут
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
