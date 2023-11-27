package com.example.amazonsystem.Service;

import com.example.amazonsystem.Model.Merchant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final ArrayList<Merchant> merchants = new ArrayList<>();


    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }

    public boolean addMerchant(Merchant merchant){
        for (Merchant merchant1:merchants) {
            if (merchant1.getId().equals(merchant.getId())){
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }

    public boolean updateMerchant(String id, Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)){
                if (merchant.getId().equals(merchants.get(i).getId())){
                    merchants.set(i,merchant);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        for (Merchant merchant:merchants) {
            if (merchant.getId().equals(id)){
                merchants.remove(merchant);
                return true;
            }
        }
        return false;
    }




}
