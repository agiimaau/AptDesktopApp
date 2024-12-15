package model;

public class Complaint {
    private int complaintId;
    private int userId;
    private String description;
    private String status; // e.g., "Pending", "Resolved"

    public Complaint(int complaintId, int userId, String description, String status) {
        this.complaintId = complaintId;
        this.userId = userId;
        this.description = description;
        this.status = status;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
