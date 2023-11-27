package com.example.amazonsystem.Service;

import com.example.amazonsystem.Model.Merchant;
import com.example.amazonsystem.Model.MerchantStock;
import com.example.amazonsystem.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final MerchantService merchantService;
    private final ProductService productService;
    private final ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    public char addMerchantStock(MerchantStock stock) {
        char check = ' ';
        char check2 = ' ';
        for (Merchant merchant : merchantService.getMerchants()) {
            if (merchant.getId().equals(stock.getMerchantId())) {
                check = 'A';
                break;
            }
        }

        for (Product product : productService.getProducts()) {
            if (product.getId().equals(stock.getProductId())) {
                check2 = 'B';
                break;
            }
        }
        for (MerchantStock merchantStock : merchantStocks) {
            if (merchantStock.getId().equals(stock.getId())) {
                return 'F';
            }
        }
        if (check == 'A' && check2 == 'B') {
            merchantStocks.add(stock);
            return 'A';
        }
        return 'E';
    }

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }


    public boolean updateMerchantStock(String id, MerchantStock merchantStock) {
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)) {
                for (Merchant merchant: merchantService.getMerchants()) {
                if (merchantStock.getMerchantId().equals(merchant.getId())){
                    for (Product product: productService.getProducts()) {
                    if (merchantStock.getProductId().equals(product.getId())) {
                        merchantStocks.set(i,merchantStock);
                        return true;
                    }
                }
                }
                }
            }
        }
        return false;
    }



    public boolean deleteMerchantStock(String id){
        for (MerchantStock merchantStock:merchantStocks) {
            if (merchantStock.getId().equals(id)){
                merchantStocks.remove(merchantStock);
                return true;
            }
        }
        return false;
    }

    //endpoint 11
    public char addToStock(String pId,String mId, int amount){
        char a,b,c;
        a=' ';
        b= ' ';
        c = ' ';
        for (MerchantStock merchantStock:merchantStocks) {
            if (merchantStock.getProductId().equals(pId) && merchantStock.getMerchantId().equals(mId)) {
                merchantStock.setStock(merchantStock.getStock() + amount);
                c ='C';
            }
        }

        for (Product product:productService.getProducts()) {
            if (product.getId().equals(pId)) {
                b= 'B';
            }}
        for (Merchant merchant: merchantService.getMerchants()) {
            if (merchant.getId().equals(mId)){
                a = 'D';
            }
            if (c != 'C' && b !='B' && a != 'D'){
                return 'E';
            }

        if (b != 'B'){
            return 'D';
        }

        }

        if (a != 'D' ){
            return 'F';
        }


        return 'A';

    }
}
