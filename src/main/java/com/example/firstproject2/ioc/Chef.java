package com.example.firstproject2.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {
    private IngredientFactory ingredientFactory;
    // 셰프가 식재료 공장과 협업하기 위한 DI ("셰프는 식재료 공장을 알고 있음") >> 외부에서 객체 정보를 받아옴.
    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu) {
        // 재료 준비
//        Pork pork = new Pork("한돈 등심");
        Ingredient ingredient = ingredientFactory.get(menu);

        // 요리 반환
        return ingredient.getName() + "으로 만든 " + menu;
    }
}
