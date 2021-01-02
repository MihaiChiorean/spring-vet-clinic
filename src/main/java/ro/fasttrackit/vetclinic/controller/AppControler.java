package ro.fasttrackit.vetclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppControler {

    @GetMapping("/api/salut")
    public String getMessage() {
        return "Salut lume!";
    }
}
