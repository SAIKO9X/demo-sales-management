package org.sales.management.model.dao.implement;

import org.sales.management.model.dao.SellerDao;
import org.sales.management.model.entities.Department;
import org.sales.management.model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        return List.of();
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        return new Seller(
                rs.getInt("Id"),
                rs.getString("Name"),
                rs.getString("Email"),
                rs.getDate("BirthDate"),
                rs.getDouble("BaseSalary"),
                department
        );
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("DepartmentId"),
                rs.getString("DepName")
        );
    }
}
