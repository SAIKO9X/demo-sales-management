package org.sales.management.model.dao.implement;

import org.sales.management.model.dao.SellerDao;
import org.sales.management.model.entities.Department;
import org.sales.management.model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        String sql = "SELECT seller.*, department.Name AS DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE seller.Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Department department = instantiateDepartment(rs);
                    return instantiateSeller(rs, department);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding seller by ID", e);
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        String sql = "SELECT seller.*, department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id ORDER BY Name";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()) {
                int departmentId = rs.getInt("DepartmentId");
                Department dep = departmentMap.computeIfAbsent(departmentId, k -> {
                    try {
                        return instantiateDepartment(rs);
                    } catch (SQLException e) {
                        throw new RuntimeException("Error instantiating department", e);
                    }
                });

                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }
            return sellers;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all sellers", e);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        String sql = "SELECT seller.*,department.Name as DepName FROM seller INNER JOIN department ON seller.DepartmentId = department.Id WHERE DepartmentId = ? ORDER BY Name";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, department.getId());

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Department dep = map.get(rs.getInt("DepartmentId"));

                    if (dep == null) {
                        dep = instantiateDepartment(rs);
                        map.put(rs.getInt("DepartmentId"), dep);
                    }

                    Seller seller = instantiateSeller(rs, dep);
                    list.add(seller);
                }
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding seller by ID", e);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        return new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), department);
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("DepartmentId"), rs.getString("DepName"));
    }
}
