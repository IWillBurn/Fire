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

        tokens.add("e3b65a8c635541af9324aded07a3799b");
        tokens.add("07e248fee69d4bf48fdef29f94a41c6f");
        tokens.add("a804be1a0d584e58a63664b31e6f820a");
        tokens.add("1e848046efd646a99a322bc10be93231");
        tokens.add("1af8810411b44294aac7247f7e09f241");
        tokens.add("d134a18b8ee945209ed1cdc1bdad266b");
        tokens.add("90e4b16af9c94af3bea9c8466c8f1f4b");
        tokens.add("e3c2af38230047f4b109cab79b9627ac");
        tokens.add("43e6885fb5f640caacf23cf6e5761557");
        tokens.add("9a83319244af4effbe826b56fac49139");
        tokens.add("02326ab38f904b24bd6f5e2fc305d733");
        tokens.add("896431c7dd54490f87ee5cb03d19aa79");
        tokens.add("c334fe734cc04185a44e2390f70b27c5");
        tokens.add("f9d0b4e81270408a92a7689a79806b4e");
        tokens.add("61ad08c1e4f146a6a5b771a2e502fd11");
        tokens.add("d10d771290da4af58f47b264dd719a9b");
        tokens.add("d518c86fbdf04e08bbba075d0703fa4d");
        tokens.add("c2d247ef4a9d4808b0bcd889ea247020");
        tokens.add("4c1d517e515e4e2daa561b832c962e08");
        tokens.add("825c2cae30ee4d4cbbe946daa1794e38");
        tokens.add("840162b4f7f84d498a402784efa0df9e");
    }

    Random random = new Random();
    public String getKey(){
        String token = tokens.get(random.nextInt(tokens.size()));
        System.out.println("now token " + token);
        return token;
    }
}
