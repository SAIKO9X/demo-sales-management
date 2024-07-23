package org.sales.management.application;

import org.sales.management.model.dao.DaoFactory;
import org.sales.management.model.dao.SellerDao;
import org.sales.management.model.entities.Seller;

public class Program {
    public static void main(String[] args) {

        System.out.println("===== TEST 1: Seller Find By Id =====");
        SellerDao sellerDao = DaoFactory.createSellerDao();
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
    }
}
