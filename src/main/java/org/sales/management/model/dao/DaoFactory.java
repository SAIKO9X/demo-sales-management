package org.sales.management.model.dao;

import org.sales.management.db.DB;
import org.sales.management.model.dao.implement.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }
}

