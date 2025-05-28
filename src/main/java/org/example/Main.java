package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.DaoImplementation;
import org.example.dto.Homework;
import org.example.dto.Student;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJpaUnit");
        EntityManager em = emf.createEntityManager();

        DaoImplementation studentDao = new DaoImplementation(em);

        em.getTransaction().begin();

        Student student = new Student();
        student.setFirstName("David");
        student.setLastName("Gilmour");
        student.setEmail("davidgilmour@example.com");

        Homework hw1 = new Homework();
        hw1.setDescription("English homework");
        hw1.setDeadline(LocalDate.now().plusDays(3));
        hw1.setMark(0);

        Homework hw2 = new Homework();
        hw2.setDescription("Physics homework");
        hw2.setDeadline(LocalDate.now().plusDays(5));
        hw2.setMark(0);

        student.addHomework(hw1);
        student.addHomework(hw2);

        studentDao.save(student);

        em.getTransaction().commit();

        Student found = studentDao.findByEmail("davidgilmour@example.com");
        System.out.println("Founded student: " + found);

        em.close();
        emf.close();
    }
}
