package com.okdk.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Firstcontroller {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model){

        model.addAttribute("username", "동키11");

        return "greetings";
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){

        model.addAttribute("username", "동키11");

        return "goodbye";
    }

}
