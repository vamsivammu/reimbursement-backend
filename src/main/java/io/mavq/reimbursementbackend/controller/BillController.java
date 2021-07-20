package io.mavq.reimbursementbackend.controller;

import io.mavq.reimbursementbackend.dto.BillDto;
import io.mavq.reimbursementbackend.dto.NewBillDto;
import io.mavq.reimbursementbackend.dto.ResponseDto;
import io.mavq.reimbursementbackend.model.Bill;
import io.mavq.reimbursementbackend.service.BillService;
import io.mavq.reimbursementbackend.service.FirebaseStorageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    BillService billService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FirebaseStorageService firebaseStorageService;

    void println(String msg){
        System.out.println(msg);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadBill(@RequestParam("file") MultipartFile file) throws Exception{
        String fileId = this.firebaseStorageService.uploadFile(file);
        return new ResponseEntity<>(fileId,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BillDto> addBill(@RequestBody NewBillDto newBillDto, @RequestAttribute Map<String,String> userData) throws ResponseStatusException{
        if(userData.get("role").equals("Admin")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin is not allowed to add bills");
        }
        newBillDto.setUserId(userData.get("id"));
        return new ResponseEntity<>(this.billService.insertBill(newBillDto,userData.get("role")),HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<BillDto>> getBills(@RequestAttribute Map<String,String> userData) throws ResponseStatusException{
        if(userData.get("role").equals("Admin")){
            return new ResponseEntity<>(this.billService.getManagerApprovedBills(),HttpStatus.OK);
        }
        if(userData.get("role").equals("Manager")){
            return new ResponseEntity<>(this.billService.getAllBills(),HttpStatus.OK);
        }
        return new ResponseEntity<>(this.billService.getAllBillsByUserId(userData.get("id")),HttpStatus.OK);
    }

    @PatchMapping("/{id}/managerOperation")
    public ResponseEntity<BillDto> updateManagerResponse(@PathVariable("id") String id, @RequestBody ResponseDto managerResponseDto, @RequestAttribute Map<String,String> userData) throws ResponseStatusException{
        if(userData.get("role").equals("Manager")){
            return new ResponseEntity<>(this.billService.updateManagerResponse(id,managerResponseDto.isStatus(),managerResponseDto.getMsg()),HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not allowed to do this operation");
        }
    }

    @PatchMapping("/{id}/adminOperation")
    public ResponseEntity<BillDto> updateAdminResponse(@PathVariable("id") String id, @RequestBody ResponseDto responseDto, @RequestAttribute Map<String,String> userData) throws ResponseStatusException{
        if(userData.get("role").equals("Admin")){
            return new ResponseEntity<>(this.billService.updateAdminResponse(id,responseDto.isStatus(),responseDto.getMsg()),HttpStatus.OK);
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"You are not allowed to do this operation");
        }
    }

}
