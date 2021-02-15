package ro.fasttrackit.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.vetclinic.model.PetDto;
import ro.fasttrackit.vetclinic.service.PetService;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {

    private final PetService service;

    public PetController(PetService injectedService) {
        this.service = injectedService;
    }

    @GetMapping("/{id}")
    public PetDto getPetById(@PathVariable Long id) {

        return service.getById(id);
    };

    @GetMapping("/all")
    public ResponseEntity<List<PetDto>> getAllPets() {

        return ResponseEntity.ok(service.getAll());
    };

    @PostMapping
    public ResponseEntity<PetDto> createNewPet(@RequestBody PetDto requestBody) {
        return new ResponseEntity<PetDto>(service.createNewPet(requestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PetDto> updatePet(@RequestBody PetDto updateRequest) {
        if (updateRequest.getId() == null || updateRequest.getId() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(updateRequest);
        }
        return ResponseEntity.ok(service.updatePet(updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}