package io.mavq.reimbursementbackend.repository;

import io.mavq.reimbursementbackend.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, String> {
    List<Bill> findByManagerAccepted(boolean managerAccepted);
    List<Bill> findByUserId(UUID userId);
    List<Bill> findAll();
    Optional<Bill> findById(UUID id);

}
