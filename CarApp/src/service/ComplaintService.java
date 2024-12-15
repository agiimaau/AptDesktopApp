package service;

import dao.ComplaintDAO;
import model.Complaint;

import java.sql.SQLException;
import java.util.List;

public class ComplaintService {
    private ComplaintDAO complaintDAO;

    public ComplaintService() throws SQLException {
        this.complaintDAO = new ComplaintDAO();
    }

    public boolean addComplaint(int userId, String description) {
        Complaint complaint = new Complaint(0, userId, description, "Pending");
        return complaintDAO.saveComplaint(complaint);
    }

    public List<Complaint> getComplaintsByUserId(int userId) {
        try {
            return complaintDAO.getComplaintsByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean resolveComplaint(int complaintId) {
        return complaintDAO.updateComplaintStatus(complaintId, "Resolved");
    }
    public List<Complaint> getAllComplaints() {
        try {
            return complaintDAO.getAllComplaints();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
