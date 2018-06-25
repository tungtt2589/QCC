package com.storm.quora.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private long personId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    public Person() {
        super();
    }

    public Person(String firstName, String lastName, int age) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long studentId) {
        this.personId = studentId;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + "]";
    }

}
