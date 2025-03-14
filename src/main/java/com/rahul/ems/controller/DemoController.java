package com.rahul.ems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
  @GetMapping("/hello")
  public String greetings() {
    return "Hello world!!";
  }
}
