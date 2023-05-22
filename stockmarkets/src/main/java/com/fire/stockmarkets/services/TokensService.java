package com.fire.stockmarkets.services;

import com.fire.stockmarkets.database.Source;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TokensService {

    List<String> tokens = new ArrayList<>();
    public TokensService(){
        tokens.add("18edf1246ab54ffeba97258cecdf3add");
        tokens.add("3c9a2879958341e590c705deab70b63f");
        tokens.add("75452c88373d47d0a38ef8b454002ff9");
        tokens.add("30908622f3d745358d50c13b3b6cc1dc");
        tokens.add("31b35983abdf46bd81aa5246089f4dfa");
        tokens.add("c6640bdd0adc43fdae41dcdf74900b1c");
        tokens.add("d7296624e8cf403981b4b61d38619fac");
        tokens.add("b8d8ee8b319c40309cfa83d013add208");
        tokens.add("7b4b0bcac9f1498d97e90ac71cd98014");
        tokens.add("38e16563085b43828a790b460f4f3b35");
        tokens.add("375fd25378f3454985b33e4409bfd9df");
        tokens.add("ea107d081fe14533b0aa56dc625a687d");
        tokens.add("0180b85e8d524c4b8795bce01d4b3389");
        tokens.add("06fa43ac33c74057a208f1fe3c4eea37");
        tokens.add("528e986d112c4baf94c142b09965f841");
        tokens.add("bece442c2416457fa2c6d385ebf26893");
        tokens.add("e1befe8453fb497d90771b73168e5b46");
        tokens.add("1d7bd378866147b8a1d56427b541d8fd");
        tokens.add("b7fc6f8df0524737ac7188eb33ee6cb7");
        tokens.add("ad72fb116b5e43739f0b5027e781d67b");
        tokens.add("98550bba2f7f4be1874585888cf9b62d");
    }

    Random random = new Random();
    public String getKey(){
        String token = tokens.get(random.nextInt(tokens.size()));
        System.out.println("now token " + token);
        return token;
    }
}
