package io.mavq.reimbursementbackend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BillDto {
    private UUID id;
    private String name;
    private String description;
    private String fileId;
    private Integer amount;
    private boolean managerAccepted;
    private boolean adminAccepted;
    private boolean managerPending;
    private boolean adminPending;
    private String managerRejectionReason;
    private String adminRejectionReason;
    private UUID userId;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private UserDto userData;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFileId() {
        return fileId;
    }

    public Integer getAmount() {
        return amount;
    }

    public boolean isManagerAccepted() {
        return managerAccepted;
    }

    public boolean isAdminAccepted() {
        return adminAccepted;
    }

    public boolean isManagerPending() {
        return managerPending;
    }

    public boolean isAdminPending() {
        return adminPending;
    }

    public String getManagerRejectionReason() {
        return managerRejectionReason;
    }

    public String getAdminRejectionReason() {
        return adminRejectionReason;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setManagerAccepted(boolean managerAccepted) {
        this.managerAccepted = managerAccepted;
    }

    public void setAdminAccepted(boolean adminAccepted) {
        this.adminAccepted = adminAccepted;
    }

    public void setManagerPending(boolean managerPending) {
        this.managerPending = managerPending;
    }

    public void setAdminPending(boolean adminPending) {
        this.adminPending = adminPending;
    }

    public void setManagerRejectionReason(String managerRejectionReason) {
        this.managerRejectionReason = managerRejectionReason;
    }

    public void setAdminRejectionReason(String adminRejectionReason) {
        this.adminRejectionReason = adminRejectionReason;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setCreated_at(LocalDateTime createdAt) {
        this.created_at = createdAt;
    }

    public void setUpdated_at(LocalDateTime updatedAt) {
        this.updated_at = updatedAt;
    }

    public UserDto getUserData() {
        return userData;
    }

    public void setUserData(UserDto userData) {
        this.userData = userData;
    }
}
