package io.mavq.reimbursementbackend.repository;

import io.mavq.reimbursementbackend.dto.BillDto;
import io.mavq.reimbursementbackend.model.Bill;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends JpaRepository<Bill, String> {

    Optional<Bill> findById(UUID id);

    @Query(value = "select new io.mavq.reimbursementbackend.dto.BillDto(bills.id, bills.name, bills.description, bills.fileId, bills.amount, bills.initialAssignedRoleId, bills.currentAssignedRoleId, bills.reason, bills.status, bills.userId, bills.createdAt, bills.updatedAt, u.name as userName, u.email as userEmail, u.role as userRole) from Bill bills join User u on u.id = bills.userId where bills.initialAssignedRoleId <= :userRole and bills.currentAssignedRoleId >= :userRole or bills.userId = :userId ")
    List<BillDto> getBills(UUID userId, Integer userRole);

}
