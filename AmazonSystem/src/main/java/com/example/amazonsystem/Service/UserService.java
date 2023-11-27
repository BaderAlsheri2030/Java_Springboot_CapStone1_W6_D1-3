package com.example.amazonsystem.Service;

import com.example.amazonsystem.Model.Merchant;
import com.example.amazonsystem.Model.MerchantStock;
import com.example.amazonsystem.Model.Product;
import com.example.amazonsystem.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    ArrayList<Product> cart = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean addUser(User user){
        for (User user1:users) {
            if (user1.getId().equals(user.getId())){
                return false;
            }
        }
    users.add(user);
        return true;
    }

    public char updateUser(String id,User user){
        for (int i = 0; i <users.size() ; i++) {
            if (users.get(i).getId().equals(id)){
                if (user.getId().equals(users.get(i).getId())){
                    users.set(i,user);
                    return 'A';
                }else return 'D';
            }
        }
        return 'F';
    }


    public boolean deleteUser(String id){
        for (User user:users) {
            if (user.getId().equals(id)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }

    //End point 12
    public char userBuy(String id,String pId,String mId){
        char a,b,c,d,e;

        a= ' ';
        b= ' ';
        c= ' ';
        d= ' ';
        e= ' ';

        double price =0;
        double balance =0;
        int count = 0;
        for (MerchantStock merchantStock:merchantStockService.getMerchantStocks()) {
            if (merchantStock.getProductId().equals(pId) && merchantStock.getMerchantId().equals(mId)) {
                c ='C';
                break;
            }
        }

        for (User user:users) {
            if (user.getId().equals(id)) {
                if (!user.getCart().isEmpty()) {
                    for (int i = 0 ; i < user.getCart().size(); i++) {
                        if (user.getCart().get(i).getId().equals(pId)) {
                            count++;
                        }
                    }
                }else {count = 1; break;}
            }
        }

        for (Product product:productService.getProducts()) {
            if (product.getId().equals(pId)) {
                b= 'B';
                price = product.getPrice()*count;
                break;
            }}
        for (Merchant merchant: merchantService.getMerchants()) {
            if (merchant.getId().equals(mId)) {
                a = 'D';
                break;
            }
        }
            if (c != 'C' && b !='B' && a != 'D'){
                return 'E';
            }

            if (b != 'B'){
                return 'D';
            }

        if (a != 'D' ){
            return 'F';
        }

        for (User user: users) {
            if (user.getId().equals(id)){
                balance =user.getBalance();
                d = 'U';
                break;
        }
        }

        if (d != 'U'){
            return 'U';
        }

        for (MerchantStock merchantStock:merchantStockService.getMerchantStocks()) {
            if (merchantStock.getProductId().equals(pId) && merchantStock.getMerchantId().equals(mId)) {
                if (merchantStock.getStock() >= count) {
                    e = 's';

                }
            }
        }
        if (e != 's'){
            return 'S';
        }

        if (balance < price){
            return 'N';
        }
        for (MerchantStock merchantStock:merchantStockService.getMerchantStocks()) {
            if (merchantStock.getProductId().equals(pId) && merchantStock.getMerchantId().equals(mId)) {
                merchantStock.setStock(merchantStock.getStock()-count);
            }
        }
        for (User user:users) {
            if (user.getId().equals(id)){
                user.setBalance(user.getBalance()-price);
            }
        }


        return 'A';
    }

    public int addToCart(String id,String pId,int stock){
        int a,b,c,d,e;
        a= 0;
        b= 0;
        c= 0;
        d= 0;
        e= 0;

        for (User user:users) {
            if (user.getId().equals(id)){
                a =1;
                break;
            }
        }
        for (User user:users) {
            if (user.getId().equals(id)) {
                if (user.getRole().equalsIgnoreCase("Customer")) {
                    b = 2;
                    break;
                }
            }
        }

            for (Product product: productService.getProducts()) {
                if (product.getId().equals(pId)){
                    c= 3;
                    break;
                }
            }

        for (MerchantStock merchantStock:merchantStockService.getMerchantStocks()) {
            if(merchantStock.getProductId().equals(pId)) {
                d = 4;
                break;
            }
        }

        for (MerchantStock merchantStock:merchantStockService.getMerchantStocks()) {
            if(merchantStock.getProductId().equals(pId)) {
                if (merchantStock.getStock() >= stock) {
                    e = 5;
                    break;
                }
            }
        }

        //user doesn't exist
        if (a == 0){
            return 1;
        }
        //user role is not customer
        if (b == 0){
            return 2;
        }
        //product doesn't exist
        if (c == 0){
            return 3;
        }
        //no available sellers
        if (d == 0){
            return 4;
        }
        //product out of stock
        if (e == 0){
            return 5;
        }

        for (User user:users) {
            if (user.getId().equals(id)){
                for (Product product: productService.getProducts()) {
                    if (product.getId().equals(pId)) {
                        for (int i = 0; i < stock; i++) {
                            user.getCart().add(product);

                        }
                    }
                }
            }
        }
        return 0;

    }

    public ArrayList<Product> displayCart(String id){
        for (User user:users) {
            if (user.getId().equals(id)){
                return user.getCart();
            }
        }
        return null;
    }

    public char checkOut(String id,String pId,String mId) {
        char check = userBuy(id, pId, mId);
        ArrayList<Product> userCart = displayCart(id);
        cart.clear();
        cart.addAll(userCart);
        for (User user : users) {
            if (user.getId().equals(id)) {
                    if (!user.getCart().isEmpty()) {
                        for (Product product : cart) {
                        if (product.getId().equals(pId)) {
                                user.getCart().remove(product);
                        }
                        }
                    }else check = 'V'; break;
                }
            }
            return check;
        }


    }

