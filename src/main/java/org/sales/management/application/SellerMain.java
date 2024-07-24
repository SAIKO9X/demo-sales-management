package org.sales.management.application;

import org.sales.management.model.dao.DaoFactory;
import org.sales.management.model.dao.SellerDao;
import org.sales.management.model.entities.Department;
import org.sales.management.model.entities.Seller;

import java.util.Date;
import java.util.List;

public class SellerMain {
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

        System.out.println("\n===== TEST 4: Seller Insert =====");
        Department newDepartment = new Department(2, null);
        Seller newSeller = new Seller(null, "Greg Muller", "greg@gmail.com", new Date(), 4000.0, newDepartment);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n===== TEST 5: Seller Update =====");
        seller = sellerDao.findById(newSeller.getId());
        seller.setName("Gregory");
        sellerDao.update(seller);
        System.out.println("Update complete!");

        System.out.println("\n===== TEST 6: Seller Delete =====");
        sellerDao.deleteById(newSeller.getId());
        System.out.println("Delete complete!");
    }
}
