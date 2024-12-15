package model;

public class Car {
    private int carId;        // Add carId field
    private int userId;
    private String carModel;
    private String carPlate;

    // Constructor with carId
    public Car(int carId, int userId, String carModel, String carPlate) {
        this.carId = carId;
        this.userId = userId;
        this.carModel = carModel;
        this.carPlate = carPlate;
    }

    // Getters and setters
    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }
}
