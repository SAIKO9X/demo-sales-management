package org.sales.management.application;

import org.sales.management.model.dao.DaoFactory;
import org.sales.management.model.dao.DepartmentDao;
import org.sales.management.model.entities.Department;

import java.util.List;

public class DepartmentMain {
    public static void main(String[] args) {

        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();

        System.out.println("===== TEST 1: Department Find By Id =====");
        Department department = departmentDao.findById(2);
        System.out.println(department);

        System.out.println("\n===== TEST 2: Department Find All =====");
        List<Department> departments = departmentDao.findAll();
        for (Department dep : departments) {
            System.out.println(dep);
        }

        System.out.println("\n===== TEST 3: Department Insert =====");
        Department newDepartment = new Department(null, "Marketing");
        departmentDao.insert(newDepartment);
        System.out.println("Inserted! New id = " + newDepartment.getId());

        System.out.println("\n===== TEST 4: Department Update =====");
        department = departmentDao.findById(newDepartment.getId());
        department.setName("Marketing Updated");
        departmentDao.update(department);
        System.out.println("Update complete!");

        System.out.println("\n===== TEST 5: Department Delete =====");
        departmentDao.deleteById(newDepartment.getId());
        System.out.println("Delete complete!");
    }
}
