package com.example.backapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class BackController {

    @GetMapping("sleep/{delayMillis}")
    public String simpleSleep(@PathVariable long delayMillis) throws InterruptedException {
        var t = Thread.currentThread();
        TimeUnit.MILLISECONDS.sleep(delayMillis);
        return "sleep " + t;
    }
}
