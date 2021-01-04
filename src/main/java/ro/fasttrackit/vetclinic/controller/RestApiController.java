package ro.fasttrackit.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.vetclinic.model.Pet;
import ro.fasttrackit.vetclinic.model.entity.PetEntity;
import ro.fasttrackit.vetclinic.service.PetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pet")
public class RestApiController {

    private final PetService petService;

    public RestApiController(PetService injectedService) {
        this.petService = injectedService;
    }

    @GetMapping("/{id}")
    public Optional<PetEntity> getPetById(@PathVariable Long id) {

        return petService.getById(id);
    };

    @GetMapping("/all")
    public ResponseEntity<List<PetEntity>> getAllPets() {

        return ResponseEntity.ok(petService.getAll());
    };

    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet requestBody) {
        return new ResponseEntity<Pet>(petService.createNewOrUpdatePet(requestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}