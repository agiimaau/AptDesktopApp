package dao;

import model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private Connection connection;

    public CarDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    public boolean deleteCar(int carId) {
        String query = "DELETE FROM cars WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateCar(Car car) throws SQLException {
        String query = "UPDATE cars SET car_model = ?, car_plate = ? WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, car.getCarModel());
            stmt.setString(2, car.getCarPlate());
            stmt.setInt(3, car.getCarId());
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean saveCar(Car car) {
        String query = "INSERT INTO cars (user_id, car_model, car_plate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, car.getUserId());
            stmt.setString(2, car.getCarModel());
            stmt.setString(3, car.getCarPlate());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Car> getCarsByHousehold(String apartmentNumber, String doorNumber) throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT c.car_id, c.user_id, c.car_model, c.car_plate " +
                       "FROM cars c " +
                       "JOIN users u ON c.user_id = u.user_id " +
                       "WHERE u.apartment_number = ? AND u.door_number = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, apartmentNumber);
            stmt.setString(2, doorNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("car_id"),
                        rs.getInt("user_id"),
                        rs.getString("car_model"),
                        rs.getString("car_plate")
                ));
            }
        }
        return cars;
    }
    public List<Car> getAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new Car(
                    rs.getInt("car_id"),
                    rs.getInt("user_id"),
                    rs.getString("car_model"),
                    rs.getString("car_plate")
                ));
            }
        }
        return cars;
    }
    public Car getCarById(int carId) {
        String query = "SELECT * FROM cars WHERE car_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, carId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Car(
                    rs.getInt("car_id"),
                    rs.getInt("user_id"),
                    rs.getString("car_model"),
                    rs.getString("car_plate")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no car is found
    }




    public List<Car> getCarsByUserId(int userId) throws SQLException {
        List<Car> cars = new ArrayList<>();
        String query = "SELECT * FROM cars WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(new Car(
                        rs.getInt("car_id"),         // Fetch and set car_id
                        rs.getInt("user_id"),        // Set user_id
                        rs.getString("car_model"),   // Set car_model
                        rs.getString("car_plate")    // Set car_plate
                ));
            }
        }
        return cars;
    }
}
