package io.mavq.reimbursementbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.mavq.reimbursementbackend.dto.NewBillDto;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Bill implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;
    private String description;
    private String fileId;
    private Integer amount;
    private Integer status;
    private Integer initialAssignedRoleId;
    private Integer currentAssignedRoleId;
    private String reason;


    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId", insertable = false,updatable = false,referencedColumnName = "id")
    private User user;

    private UUID userId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Bill() {
    }

    public Bill(NewBillDto newBillDto, Integer userRoleId) {
        this.name = newBillDto.getName();
        this.description = newBillDto.getDescription();
        this.fileId = newBillDto.getFileId();
        this.amount = newBillDto.getAmount();
        this.initialAssignedRoleId = userRoleId + 1;
        this.currentAssignedRoleId = this.initialAssignedRoleId;
        this.userId = UUID.fromString(newBillDto.getUserId());
        this.status = 0;
        this.reason = "";
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
