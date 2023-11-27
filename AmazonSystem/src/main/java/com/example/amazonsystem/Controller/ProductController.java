package com.example.amazonsystem.Controller;

import com.example.amazonsystem.Model.Product;
import com.example.amazonsystem.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {
private final ProductService productService;

@GetMapping("/get")
public ResponseEntity getProducts(){
    return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
}

@PostMapping("/add")
public ResponseEntity addProduct(@Valid @RequestBody Product product, Errors errors ){
    if (errors.hasErrors()){
        String message = errors.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    char isValid = productService.addProduct(product);
    if(isValid == 'D'){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with the same ID already exists");
    }
    if (isValid == 'A'){
        return ResponseEntity.status(HttpStatus.OK).body("Product Added");
    }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Category ID or Category may not exist,");
}

@PutMapping("/update/{id}")
public ResponseEntity updateProduct(@PathVariable String id, @Valid @RequestBody Product product, Errors errors){
    if (errors.hasErrors()){
        String message = errors.getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    boolean isValid = productService.updateProduct(id,product);
    if (isValid){
        return ResponseEntity.status(HttpStatus.OK).body("Product Updated");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or product doesn't exist. (ID cannot be updated)");
}
@DeleteMapping("/delete/{id}")
public ResponseEntity deleteProduct(@PathVariable String id){
    boolean isValid = productService.deleteProduct(id);
    if(isValid){
        return ResponseEntity.status(HttpStatus.OK).body("Product Deleted");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or product doesn't exist");
}
}
