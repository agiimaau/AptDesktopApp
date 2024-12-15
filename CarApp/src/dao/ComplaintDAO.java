package dao;

import model.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {
    private Connection connection;

    public ComplaintDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean saveComplaint(Complaint complaint) {
        String query = "INSERT INTO complaints (user_id, description, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, complaint.getUserId());
            stmt.setString(2, complaint.getDescription());
            stmt.setString(3, complaint.getStatus());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                complaint.setComplaintId(generatedKeys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Complaint> getComplaintsByUserId(int userId) throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                complaints.add(new Complaint(
                        rs.getInt("complaint_id"),
                        rs.getInt("user_id"),
                        rs.getString("description"),
                        rs.getString("status")
                ));
            }
        }
        return complaints;
    }

    public boolean updateComplaintStatus(int complaintId, String status) {
        String query = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, complaintId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Complaint> getAllComplaints() throws SQLException {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                complaints.add(new Complaint(
                        rs.getInt("complaint_id"),
                        rs.getInt("user_id"),
                        rs.getString("description"),
                        rs.getString("status")
                ));
            }
        }
        return complaints;
    }
}
