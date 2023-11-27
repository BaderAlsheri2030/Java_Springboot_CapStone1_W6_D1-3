package com.example.amazonsystem.Service;

import com.example.amazonsystem.Model.Category;
import com.example.amazonsystem.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryService categoryService;
    private final ArrayList<Product> products = new ArrayList<>();



    public char addProduct(Product product){
        //product ID must be unique and not duplicated
        for (Product product1: products) {
            if (product1.getId().equals(product.getId())){
                    return 'D';
            }
        }
        //Category ID must exist and must match product category ID
        for (int i = 0; i < categoryService.getCategories().size(); i++) {
            if (categoryService.getCategories().get(i).getId().equals(product.getCategoryID())) {
                products.add(product);
                return 'A';
            }
        }
        //Category ID doesn't exist
        return 'F';

    }
    public ArrayList<Product> getProducts(){
        return products;
    }

    public boolean updateProduct(String id, Product product){
        for (int i = 0; i <products.size() ; i++) {
            if (products.get(i).getId().equals(id)){
                if (products.get(i).getId().equals(product.getId())) {
                    for (Category category : categoryService.getCategories()) {
                        if (category.getId().equals(product.getCategoryID())) {
                            products.set(i, product);
                            return true;

                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        for (Product p:products) {
            if (p.getId().equals(id)){
                products.remove(p);
                return true;
            }
        }
        return false;
    }



}
