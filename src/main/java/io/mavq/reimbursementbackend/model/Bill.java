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
    private boolean managerAccepted;
    private boolean adminAccepted;
    private boolean managerPending;
    private boolean adminPending;
    private String managerRejectionReason;
    private String adminRejectionReason;

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

    public Bill(NewBillDto newBillDto) {
        this.name = newBillDto.getName();
        this.description = newBillDto.getDescription();
        this.fileId = newBillDto.getFileId();
        this.userId = UUID.fromString(newBillDto.getUserId());
        this.amount = newBillDto.getAmount();
        this.managerAccepted = false;
        this.adminAccepted = false;
        this.managerPending = true;
        this.adminPending = true;
        this.managerRejectionReason = "";
        this.adminRejectionReason = "";
    }

    public UUID getId() {
        return id;
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

    public Integer getAmount(){return this.amount;}

    public boolean isManagerAccepted() {
        return managerAccepted;
    }

    public void setManagerAccepted(boolean managerAccepted) {
        this.managerAccepted = managerAccepted;
    }

    public boolean isAdminAccepted() {
        return adminAccepted;
    }

    public void setAdminAccepted(boolean adminAccepted) {
        this.adminAccepted = adminAccepted;
    }

    public boolean isManagerPending() {
        return managerPending;
    }

    public void setManagerPending(boolean managerPending) {
        this.managerPending = managerPending;
    }

    public boolean isAdminPending() {
        return adminPending;
    }

    public void setAdminPending(boolean adminPending) {
        this.adminPending = adminPending;
    }

    public String getManagerRejectionReason() {
        return managerRejectionReason;
    }

    public void setManagerRejectionReason(String managerRejectionReason) {
        this.managerRejectionReason = managerRejectionReason;
    }

    public String getAdminRejectionReason() {
        return adminRejectionReason;
    }

    public void setAdminRejectionReason(String adminRejectionReason) {
        this.adminRejectionReason = adminRejectionReason;
    }

    public User getUser() {
        return user;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUser(User user) {
        this.user = user;
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
