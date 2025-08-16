package vn.longlee.jobhunter.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.longlee.jobhunter.util.error.IdInvalidException;

@RestController
public class HelloController {
    @GetMapping("/")
    @CrossOrigin
    public String getHelloWorld() throws IdInvalidException {
//        if (true) {
//            throw new IdInvalidException("Xin Chào LongLEE");
//        }
        return "Hello World (LongLee)";
    }
}
