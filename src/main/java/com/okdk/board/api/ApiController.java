package com.okdk.board.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
public class ApiController {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/api/now")
    public String now() {
        Date nowDate = new Date();

        return formatter.format(nowDate);
    }

    @GetMapping("/api/now/json")
    public HashMap<String, Object> nowJson() {
        HashMap<String, Object> retMap = new HashMap<>();

        Date nowDate = new Date();

        retMap.put("now", formatter.format(nowDate));

        return retMap;
    }





}
