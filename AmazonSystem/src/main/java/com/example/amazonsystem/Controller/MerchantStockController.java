package com.example.amazonsystem.Controller;

import com.example.amazonsystem.Model.MerchantStock;
import com.example.amazonsystem.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;
    @GetMapping("/get")
    public ResponseEntity getMerchantStock(){
        return ResponseEntity.status(HttpStatus.OK).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        char valid = merchantStockService.addMerchantStock(merchantStock);
        if (valid == 'F'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant Stock with the same ID already exists");
        }
        if (valid == 'A'){
            return ResponseEntity.status(HttpStatus.OK).body("Merchant Stock added");

        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Product or Merchant ID (product and Merchant IDs must exist)");

    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @Valid @RequestBody MerchantStock merchantStock,Errors errors){
    if (errors.hasErrors()){
        String message = errors.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    boolean isValid = merchantStockService.updateMerchantStock(id,merchantStock);
    if (isValid){
        return ResponseEntity.status(HttpStatus.OK).body("Merchant Stock Updated");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid MerchantStock or doesn't exist(Product and merchant IDs must exist)");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id){
        boolean isValid = merchantStockService.deleteMerchantStock(id);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("Merchant Stock deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or doesn't exist");
    }

    @PutMapping("/addstock/{pId}/{mId}/{amount}")
    public ResponseEntity addToStock(@PathVariable String pId ,@PathVariable String mId,@PathVariable int amount) {
        char isValid = merchantStockService.addToStock(pId, mId, amount);
        if (isValid == 'A') {
            return ResponseEntity.status(HttpStatus.OK).body("Amount added to Stock");
        }
        if (isValid == 'F') {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid merchant ID");
        }
        if (isValid == 'D'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Product ID");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant stock doesn't exist");
    }


}
