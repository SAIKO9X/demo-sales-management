package org.sales.management.model.dao.implement;

import org.sales.management.model.dao.DepartmentDao;
import org.sales.management.model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        String sql = "INSERT INTO department (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, department.getName());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        department.setId(generatedKeys.getInt(1));
                    }
                }
            } else {
                throw new RuntimeException("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting department", e);
        }
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE department SET Name = ? WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating department", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM department WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting department", e);
        }
    }

    @Override
    public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instantiateDepartment(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding department by ID", e);
        }
        return null;
    }

    @Override
    public List<Department> findAll() {
        String sql = "SELECT * FROM department ORDER BY Name";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Department> list = new ArrayList<>();
            while (rs.next()) {
                Department dep = instantiateDepartment(rs);
                list.add(dep);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all departments", e);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getInt("Id"),
                rs.getString("Name")
        );
    }
}
