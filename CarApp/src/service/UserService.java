package service;

import dao.CarDAO;
import dao.UserDAO;
import model.Car;
import model.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserDAO userDAO;
    private CarDAO carDAO; 

    public UserService() throws SQLException {
        userDAO = new UserDAO();
        carDAO = new CarDAO(); // Initialize CarDAO
    }

    public boolean registerUser(String lastname, String firstname, String apartmentNumber, String doorNumber, String username, String password) {
        User existingUser = userDAO.getUserByUsername(username);
        if (existingUser != null) {
            return false; // Username already exists
        }
        User newUser = new User(lastname, firstname, apartmentNumber, doorNumber, username, password, "liver");
        return userDAO.saveUser(newUser);
    }

    public User loginUser(String username, String password) {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user; // Return user with their role
        }
        return null; // Login failed
    }
    public boolean saveUser(User user) {
        return userDAO.saveUser(user);
    }
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Car> getAllCars() {
        try {
            return carDAO.getAllCars();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




    public List<User> getUsersByApartment(String apartmentNumber, String doorNumber) {
        return userDAO.getUsersByApartment(apartmentNumber, doorNumber);
    }
    public List<User> getLiverUsers() {
        return userDAO.getUsersByRole("liver");
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }
}
