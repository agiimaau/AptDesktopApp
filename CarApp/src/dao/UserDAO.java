package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;

    public UserDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean saveUser(User user) {
        String query = "INSERT INTO users (lastname, firstname, apartment_number, door_number, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getLastname());
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getApartmentNumber());
            stmt.setString(4, user.getDoorNumber());
            stmt.setString(5, user.getUsername());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getRole());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1)); // Set the generated user_id
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"), // Fetch user_id
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("apartment_number"),
                        rs.getString("door_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsersByApartment(String apartmentNumber, String doorNumber) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE apartment_number = ? AND door_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, apartmentNumber);
            stmt.setString(2, doorNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"), // Fetch user_id
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("apartment_number"),
                        rs.getString("door_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getUsersByRole(String role) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"), // Fetch user_id
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("apartment_number"),
                        rs.getString("door_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("lastname"),
                        rs.getString("firstname"),
                        rs.getString("apartment_number"),
                        rs.getString("door_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateUser(User user) throws SQLException {
        String query = "UPDATE users SET lastname = ?, firstname = ?, apartment_number = ?, door_number = ?, username = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getLastname());
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getApartmentNumber());
            stmt.setString(4, user.getDoorNumber());
            stmt.setString(5, user.getUsername());
            stmt.setInt(6, user.getUserId());
            return stmt.executeUpdate() > 0;
        }
    }

    

}
