package io.mavq.reimbursementbackend.controller;

import io.mavq.reimbursementbackend.dto.NewBillDto;
import io.mavq.reimbursementbackend.dto.ResponseDto;
import io.mavq.reimbursementbackend.model.Bill;
import io.mavq.reimbursementbackend.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    void println(String msg){
        System.out.println(msg);
    }

    @PostMapping()
    public ResponseEntity<Bill> addBill(@RequestBody NewBillDto newBillDto, @RequestAttribute Map<String,String> userData) throws Exception{
        println("add bill");
        if(userData.get("role").matches("Admin")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin is not allowed to add bills");
        }
        newBillDto.setUserId(userData.get("id"));
        return new ResponseEntity<>(this.billService.insertBill(newBillDto,userData.get("role")),HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Bill>> getBills(@RequestAttribute Map<String,String> userData) throws Exception{
        println("get bills");
        if(userData.get("role").matches("Admin")){
            return new ResponseEntity<>(this.billService.getManagerApprovedBills(),HttpStatus.OK);
        }
        if(userData.get("role").matches("Manager")){
            return new ResponseEntity<>(this.billService.getAllBills(),HttpStatus.OK);
        }
        return new ResponseEntity<>(this.billService.getAllBillsByUserId(userData.get("id")),HttpStatus.OK);
    }

    @PatchMapping("/{id}/managerOperation")
    public void updateManagerResponse(@PathVariable("id") String id, @RequestBody ResponseDto managerResponseDto, @RequestAttribute Map<String,String> userData) throws Exception{
        println("hi manager");
        println(id);
        if(userData.get("role").equals("Manager")){
            this.billService.updateManagerResponse(id,managerResponseDto.isStatus(),managerResponseDto.getMsg());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/adminOperation")
    public void updateAdminResponse(@PathVariable("id") String id, @RequestBody ResponseDto responseDto, @RequestAttribute Map<String,String> userData) throws Exception{
        println("hi admin");
        println(id);
        if(userData.get("role").equals("Admin")){
            this.billService.updateAdminResponse(id,responseDto.isStatus(),responseDto.getMsg());
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
