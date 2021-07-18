package io.mavq.reimbursementbackend.service;

import io.mavq.reimbursementbackend.dto.NewBillDto;
import io.mavq.reimbursementbackend.model.Bill;
import io.mavq.reimbursementbackend.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    public BillService(){

    }

    public Bill insertBill(NewBillDto newBillDto,String role){
        Bill bill = new Bill(newBillDto);
        if(role.equals("Manager")){
            bill.setManagerPending(false);
            bill.setManagerAccepted(true);
        }
        return this.billRepository.save(bill);
    }

    public List<Bill> getAllBillsByUserId(String userId){
        UUID id = UUID.fromString(userId);
        return this.billRepository.findByUserId(id);
    }

    public List<Bill> getAllBills(){
        return this.billRepository.findAll();
    }

    public List<Bill> getManagerApprovedBills(){
        return this.billRepository.findByManagerAccepted(true);
    }

    public void updateManagerResponse(String billId,boolean status, String msg){
        UUID id = UUID.fromString(billId);
        Optional<Bill> bill = this.billRepository.findById(id);
        if(bill.isPresent()){
            Bill billData = bill.get();
            billData.setManagerAccepted(status);
            billData.setManagerRejectionReason(msg);
            billData.setManagerPending(false);
            this.billRepository.save(billData);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bill not found");
        }
    }

    public void updateAdminResponse(String billId,boolean status,String msg){
        UUID id = UUID.fromString(billId);
        Optional<Bill> bill = this.billRepository.findById(id);
        if(bill.isPresent()){
            Bill billData = bill.get();
            billData.setAdminAccepted(status);
            billData.setAdminPending(false);
            billData.setAdminRejectionReason(msg);
            this.billRepository.save(billData);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bill not found");
        }
    }
}
