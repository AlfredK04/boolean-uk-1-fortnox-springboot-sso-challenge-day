package org.booleanuk.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthTestController {

    @GetMapping("public/test")
    public String getPublicHello() {

        return "Public Hello, World!";
    }

    @GetMapping("test")
    public String getPrivateHello() {

        return "Private Hello, World";
    }
}