package io.mavq.reimbursementbackend.service;

import io.mavq.reimbursementbackend.dto.BillDto;
import io.mavq.reimbursementbackend.dto.NewBillDto;
import io.mavq.reimbursementbackend.dto.UserDto;
import io.mavq.reimbursementbackend.model.Bill;
import io.mavq.reimbursementbackend.model.User;
import io.mavq.reimbursementbackend.repository.BillRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserService userService;

    public BillService(){

    }

    public BillDto insertBill(NewBillDto newBillDto,String role){
        Bill bill = new Bill(newBillDto);
        System.out.println(role);
        if(role.equals("Manager")){
            bill.setManagerPending(false);
            bill.setManagerAccepted(true);
        }
        bill = this.billRepository.save(bill);
        Optional<User> user = this.userService.userRepository.findById(UUID.fromString(newBillDto.getUserId()));
        bill.setUser(user.get());
        return  this.billEntityToDto(bill);
    }

    public List<BillDto> getAllBillsByUserId(String userId){
        UUID id = UUID.fromString(userId);
        List<Bill> bills = this.billRepository.findByUserId(id, Sort.by(Sort.Direction.DESC,"createdAt"));
        return bills.stream().map(this::billEntityToDto).collect(Collectors.toList());
    }

    public List<BillDto> getAllBills(){
        List<Bill> bills = this.billRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
        return bills.stream().map(this::billEntityToDto).collect(Collectors.toList());
    }

    public List<BillDto> getManagerApprovedBills(){
        List<Bill> bills = this.billRepository.findByManagerAccepted(true,Sort.by(Sort.Direction.DESC,"createdAt"));
        return bills.stream().map(this::billEntityToDto).collect(Collectors.toList());
    }

    public BillDto updateManagerResponse(String billId,boolean status, String msg){
        UUID id = UUID.fromString(billId);
        Optional<Bill> bill = this.billRepository.findById(id);
        if(bill.isPresent()){
            Bill billData = bill.get();
            billData.setManagerAccepted(status);
            billData.setManagerRejectionReason(msg);
            billData.setManagerPending(false);
            Bill updatedBill = this.billRepository.save(billData);
            BillDto billDto = this.billEntityToDto(updatedBill);
            return billDto;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bill not found");
        }
    }

    public BillDto updateAdminResponse(String billId,boolean status,String msg){
        UUID id = UUID.fromString(billId);
        Optional<Bill> bill = this.billRepository.findById(id);
        if(bill.isPresent()){
            Bill billData = bill.get();
            billData.setAdminAccepted(status);
            billData.setAdminPending(false);
            billData.setAdminRejectionReason(msg);
            Bill updatedBill = this.billRepository.save(billData);
            BillDto billDto = this.billEntityToDto(updatedBill);
            return billDto;
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bill not found");
        }
    }

    private BillDto billEntityToDto(Bill bill){
        BillDto billDto = this.modelMapper.map(bill,BillDto.class);
        billDto.setUserData(new UserDto(bill.getUser()));
        return billDto;
    }
}
