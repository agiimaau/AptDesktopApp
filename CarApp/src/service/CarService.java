package service;

import dao.CarDAO;
import model.Car;

import java.sql.SQLException;
import java.util.List;

public class CarService {
    private CarDAO carDAO;

    public CarService() throws SQLException {
        carDAO = new CarDAO();
    }

    // Add a car for a user
    public boolean addCar(int userId, String carModel, String carPlate) {
        try {
            // Ensure the user doesn't already have 2 cars
            List<Car> userCars = carDAO.getCarsByUserId(userId);
            if (userCars.size() >= 2) {
                return false; // Max 2 cars allowed
            }

            // Create and save the car
            Car car = new Car(0, userId, carModel, carPlate); // carId = 0 for new cars
            return carDAO.saveCar(car);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Car getCarById(int carId) {
        return carDAO.getCarById(carId);
    }
    public List<Car> getCarsByHousehold(String apartmentNumber, String doorNumber) {
        try {
            return carDAO.getCarsByHousehold(apartmentNumber, doorNumber);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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


    // Delete a car by carId
    public boolean deleteCar(int carId) {
        return carDAO.deleteCar(carId);
    }

    // Get all cars for a specific user
    public List<Car> getCarsByUserId(int userId) {
        try {
            return carDAO.getCarsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Update car details
    public boolean updateCar(Car car) {
        try {
            return carDAO.updateCar(car);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
