package com.okdk.board.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class boardController {

    @RequestMapping(value = "/baord", method = RequestMethod.POST)
    public String board() {
        

        return "board";
    }
}
