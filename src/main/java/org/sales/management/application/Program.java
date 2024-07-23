package org.sales.management.application;

import org.sales.management.model.dao.DaoFactory;
import org.sales.management.model.dao.SellerDao;
import org.sales.management.model.entities.Department;
import org.sales.management.model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        System.out.println("===== TEST 1: Seller Find By Id =====");
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n===== TEST 2: Seller Find By Department =====");
        Department department = new Department(2, null);
        List<Seller> sellers = sellerDao.findByDepartment(department);
        for (Seller s : sellers) {
            System.out.println(s);
        }

        System.out.println("\n===== TEST 3: Seller Find All =====");
        List<Seller> allSellers = sellerDao.findAll();
        for (Seller s : allSellers) {
            System.out.println(s);
        }
    }
}
