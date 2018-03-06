package fr.lyon1.avote.persistence.dao;

import fr.lyon1.avote.logic.model.entity.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;

@Repository
public class DepartmentDAO {

    @Autowired
    SessionFactory sessionFactory;

    public Department getDepartmentByPostalCode(String postalCode) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Department> q = session.createQuery("SELECT department FROM Department department WHERE department.department = :code", Department.class);
        q.setParameter("code", postalCode.substring(0, 2));
        return q.getSingleResult();
    }


}
