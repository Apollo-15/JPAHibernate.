package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.dto.Student;

import java.util.List;

public class DaoImplementation implements GenericDao<Student, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public DaoImplementation(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    public Student findByEmail(String email) {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class).setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public List<Student> findAll() {
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
        return query.getResultList();
    }

    @Override
    public Student update(Student entity) {
        Student existing = entityManager.find(Student.class, entity.getId());
        if (existing != null) {
            existing.setFirstName(entity.getFirstName());
            existing.setLastName(entity.getLastName());
            existing.setEmail(entity.getEmail());
            return existing;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Student byId = findById(id);
        if (byId != null) {
            entityManager.remove(byId);
            return true;
        }
        return false;
    }
}
