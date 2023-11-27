package com.example.amazonsystem.Controller;

import com.example.amazonsystem.Model.User;
import com.example.amazonsystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isValid = userService.addUser(user);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("User Added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with the same ID already exist");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors){
        if (errors.hasErrors()){
            String message  = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        char valid = userService.updateUser(id,user);
        if (valid =='D'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot change or update User ID");
        }
        if (valid == 'A'){
            return ResponseEntity.status(HttpStatus.OK).body("User Updated");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User ID or user doesn't exist");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        boolean isValid = userService.deleteUser(id);
        if (isValid) {
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or user doesn't exist");
    }



    @GetMapping("/buy/{id}/{pId}/{mId}")
    public ResponseEntity userBuy(@PathVariable String id,@PathVariable String pId,@PathVariable String mId){
        char check = userService.userBuy(id,pId,mId);
        if (check == 'E'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant Stock doesn't exist");
        }
        if (check == 'D'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Product may not exist ");
        }
        if (check == 'F'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Merchant doesn't exist");
        }
        if (check == 'U'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or user doesn't exist");
        }
        if (check == 'S'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock");
        }
        if(check =='N'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product Purchased");
    }

    @PutMapping("/cart/{id}/{pId}/{stock}")
    public ResponseEntity addToCart(@PathVariable String id,@PathVariable String pId,@PathVariable int stock){
        int check = userService.addToCart(id,pId,stock);
        if (check == 1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user Id or User doesn't exist");
        }
        if (check == 2){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role of user must be customer in order to add to cart");
        }
        if (check == 3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product ID or product doesn't exist");
        }
        if(check == 4){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid MerchantStock ID or no available sellers");
        }
        if (check == 5){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product added to cart");
    }

    @GetMapping("/cart/get/{id}")
    public ResponseEntity displayCart(@PathVariable String id){
        if (userService.displayCart(id) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User ID");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.displayCart(id));
    }
    @PutMapping("/checkout/{id}/{pId}/{mId}")
    public ResponseEntity checkOut(@PathVariable String id, @PathVariable String pId,@PathVariable String mId){
        char check = userService.checkOut(id,pId,mId);

        if (check == 'D'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Product may not exist ");
        }
        if (check == 'E'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant Stock doesn't exist");
        }
        if (check == 'F'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Merchant doesn't exist");
        }
        if (check == 'U'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or user doesn't exist");
        }
        if (check == 'S'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock");
        }
        if(check =='N'){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
        if (check == 'V') {
            return ResponseEntity.status(HttpStatus.OK).body("Cart is empty, you may want to add products to your cart");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Product Purchased");
    }











}
