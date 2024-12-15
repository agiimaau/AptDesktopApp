package model;

public class User {
    private int userId; // Added userId
    private String lastname;
    private String firstname;
    private String apartmentNumber;
    private String doorNumber;
    private String username;
    private String password;
    private String role;

    // Constructor with userId
    public User(int userId, String lastname, String firstname, String apartmentNumber, String doorNumber, String username, String password, String role) {
        this.userId = userId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.apartmentNumber = apartmentNumber;
        this.doorNumber = doorNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Constructor without userId (for new users)
    public User(String lastname, String firstname, String apartmentNumber, String doorNumber, String username, String password, String role) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.apartmentNumber = apartmentNumber;
        this.doorNumber = doorNumber;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
