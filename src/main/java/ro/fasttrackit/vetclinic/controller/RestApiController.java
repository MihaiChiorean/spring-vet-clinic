package ro.fasttrackit.vetclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.fasttrackit.vetclinic.model.Pet;
import ro.fasttrackit.vetclinic.service.PetService;

@RestController
public class RestApiController {

    private final PetService petService;

    public RestApiController(PetService injectedService) {
        this.petService = injectedService;
    }

    @GetMapping("/api/home")
    public String homePage() {
        return "This is the home page";
    };

    @GetMapping("/api/getPet")
    public String getPet() {
        return "return pet!";
    };

    @GetMapping("/api/getPets")
    public String getPets() {
        return "return pets";
    };

    @PostMapping("/api/addPet")
    public Pet createPet(@RequestBody Pet requestBody) {
        return petService.createNewPet(requestBody);
    }

}