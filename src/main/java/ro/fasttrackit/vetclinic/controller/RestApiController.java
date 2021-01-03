package ro.fasttrackit.vetclinic.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.vetclinic.model.Pet;
import ro.fasttrackit.vetclinic.model.entity.PetEntity;
import ro.fasttrackit.vetclinic.service.PetService;

import java.util.List;
import java.util.Optional;

@RestController
public class RestApiController {

    private final PetService petService;

    public RestApiController(PetService injectedService) {
        this.petService = injectedService;
    }

    @GetMapping("/api/getPet/{id}")
    public Optional<PetEntity> getPetById(@PathVariable Long id) {

        return petService.getById(id);
    };

    @GetMapping("/api/getAllPets")
    public List<PetEntity> getAllPets() {

        return petService.getAll();
    };

    @PostMapping("/api/addPet")
    public Pet createPet(@RequestBody Pet requestBody) {
        return petService.createNewPet(requestBody);
    }

    @DeleteMapping("/api/{id}")
    public void deletePet(@PathVariable Long id) {
        petService.deleteById(id);
    }

}