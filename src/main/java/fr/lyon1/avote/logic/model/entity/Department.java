package fr.lyon1.avote.logic.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENTS")
public class Department {

    @Id
    @Column(name = "department_id")
    private String departmentId;
    @Column(name = "department")
    private String department;

    public Department() {
    }

    public Department(String departmentId, String department) {
        this.departmentId = departmentId;
        this.department = department;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
