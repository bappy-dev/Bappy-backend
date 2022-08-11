package spring.bappy.controllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.bappy.domain.Hangout.HangoutInfo;
import spring.bappy.service.HangoutService;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final HangoutService hangoutService;

    @Autowired
    public TestController(HangoutService hangoutService) {
        this.hangoutService = hangoutService;
    }

    @GetMapping("")
    public void test() {

        ArrayList<String> category = new ArrayList<String>();
        category.add("sport");
        category.add("play");
        //return hangoutService.getHangoutInfoByCategory(category);
    }
}
