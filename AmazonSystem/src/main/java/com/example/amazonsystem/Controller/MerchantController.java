package com.example.amazonsystem.Controller;

import com.example.amazonsystem.Model.Merchant;
import com.example.amazonsystem.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchants(){
        return ResponseEntity.status(HttpStatus.OK).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@Valid @RequestBody Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isValid = merchantService.addMerchant(merchant);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("Merchant Added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant with the same ID already Exists");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @Valid @RequestBody Merchant merchant,Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isValid = merchantService.updateMerchant(id,merchant);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("Merchant is updated");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Merchant doesn't exist. (ID cannot be updated)");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id){
        boolean isValid = merchantService.deleteMerchant(id);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("Merchant is deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or merchant doesn't exist");
    }

}
