package io.mavq.reimbursementbackend.dto;

public class NewBillDto {
    private String name;
    private String description;
    private String userId;
    private String fileId;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
