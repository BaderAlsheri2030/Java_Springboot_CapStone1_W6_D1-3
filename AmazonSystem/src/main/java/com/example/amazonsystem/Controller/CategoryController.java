package com.example.amazonsystem.Controller;

import com.example.amazonsystem.Model.Category;
import com.example.amazonsystem.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }
    @PostMapping("/add")
    public ResponseEntity addCategory(@Valid @RequestBody Category category, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isValid = categoryService.addCategory(category);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("Category added");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with same ID already exists");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @Valid @RequestBody Category category,Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        boolean isValid =categoryService.updateCategory(id,category);
        if (isValid){
            return ResponseEntity.status(HttpStatus.OK).body("Category is updated");
        }
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or Category doesn't exist. (ID cannot be updated)");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id){
        boolean isDeleted = categoryService.deleteCategory(id);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Category deleted");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID or category doesn't exist");
    }

}
