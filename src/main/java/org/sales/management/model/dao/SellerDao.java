package org.sales.management.model.dao;

import org.sales.management.model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller seller);

    void update(Seller seller);

    void deleteById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();
}
