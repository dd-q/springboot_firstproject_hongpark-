package com.example.firstproject2.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BurgerTest {

    @Test                            // .writeValueAsString() 의 예외처리
    public void 자바객체를_JSON으로_변환() throws JsonProcessingException {
        // 준비
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        Burger burger = new Burger("맥도날드 슈비버거", 5500, ingredients);


        // 수행
        String json = objectMapper.writeValueAsString(burger);
                                // Serialize 에러가 난다면, 해당 객체 클래스에 Getter가 없다는 뜻. >> JSON으로 만들기위해 값을 가져와야함.

        // 예상
        String expected = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우 패티\",\"순쇠고기 패티\",\"토마토\",\"스파이시 어니언 소스\"]}";

        // 검증
        assertEquals(expected, json);
        JsonNode jsonNode = objectMapper.readTree(json); // json을 파라미터로 받아 .readTree 해주면 JsonNode 클래스가 됨
        System.out.println(jsonNode.toPrettyString()); // .toPrettyString 메서드를 쓰면 정렬된 Json 데이터를 받아볼 수 있다.
    }

    @Test                          // .readValue() 의 예외처리
    public void JSON을_자바객체로_변환() throws JsonProcessingException {
        // 준비
        ObjectMapper objectMapper = new ObjectMapper();
//        String json = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우 패티\",\"순쇠고기 패티\",\"토마토\",\"스파이시 어니언 소스\"]}";

        // json을 하드코딩하지말고 직접 만들어보자.
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토", "스파이시 어니언 소스");
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("name", "맥도날드 슈비버거");
        objectNode.put("price", 5500);

        ArrayNode arrayNode =objectMapper.createArrayNode();
        for (String i : ingredients){
            arrayNode.add(i);
        }
        objectNode.set("ingredients", arrayNode);
        String json = objectNode.toString();

        // 수행
        Burger burger = objectMapper.readValue(json, Burger.class);

        // 예상

        Burger expected =  new Burger("맥도날드 슈비버거", 5500, ingredients);


        // 검증
        assertEquals(expected.toString(), burger.toString());     // Cannot construct instance 에러가 난다면, 기본생성자 (@NoArgsConstructor) 를 추가해줄 것.
        System.out.println(burger);
        System.out.println(expected);
        System.out.println(json);
    }

}