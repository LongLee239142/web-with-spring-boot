package vn.hoidanit.jobhunter.controller;

import ch.qos.logback.classic.spi.IThrowableProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.jobhunter.service.error.IdInvalidException;

@RestController
public class HelloController {
    @GetMapping("/")
    public String getHelloWorld() throws IdInvalidException {
        if (true) {
            throw new IdInvalidException("Xin Chào LongLEE");
        }
        return "Hello World (LongLee)";
    }
}
