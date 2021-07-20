package io.mavq.reimbursementbackend.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BillDto {
    private UUID id;
    private String name;
    private String description;
    private String fileId;
    private Integer amount;
    private Integer initialAssignedRoleId;
    private Integer currentAssignedRoleId;
    private String reason;
    private Integer status;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userName;
    private String userEmail;
    private Integer userRole;

    public BillDto() {
    }

    public BillDto(UUID id, String name, String description, String fileId, Integer amount, Integer initialAssignedRoleId, Integer currentAssignedRoleId, String reason, Integer status, UUID userId, LocalDateTime createdAt, LocalDateTime updatedAt, String userName, String userEmail, Integer userRole) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileId = fileId;
        this.amount = amount;
        this.initialAssignedRoleId = initialAssignedRoleId;
        this.currentAssignedRoleId = currentAssignedRoleId;
        this.reason = reason;
        this.status = status;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getInitialAssignedRoleId() {
        return initialAssignedRoleId;
    }

    public void setInitialAssignedRoleId(Integer initialAssignedRoleId) {
        this.initialAssignedRoleId = initialAssignedRoleId;
    }

    public Integer getCurrentAssignedRoleId() {
        return currentAssignedRoleId;
    }

    public void setCurrentAssignedRoleId(Integer currentAssignedRoleId) {
        this.currentAssignedRoleId = currentAssignedRoleId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }


}
