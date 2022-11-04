package com.example.firstproject2.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 일반 Controller는 View Template 페이지를 반환하지만, RestController는
@RestController // RestAPI 용 컨트롤러! (기본적으로) JSON 을 반환!
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello(){
        return "hello world";
    }

}
