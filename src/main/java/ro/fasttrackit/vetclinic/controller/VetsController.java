package ro.fasttrackit.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.vetclinic.model.VetsDto;
import ro.fasttrackit.vetclinic.service.VetsService;

import java.util.List;

@RestController
@RequestMapping("api/vet")
public class VetsController {

    private final VetsService service;

    public VetsController(VetsService injectedService) {
        this.service = injectedService;
    }

    @GetMapping("/{id}")
    public VetsDto getVetById(@PathVariable(name = "id") Long vetId) {

        return service.getById(vetId);
    };

    @GetMapping("/all")
    public ResponseEntity<List<VetsDto>> getAllVets() {

        return ResponseEntity.ok(service.getAll());
    };

    @PostMapping
    public ResponseEntity<VetsDto> createNewVet(@RequestBody VetsDto requestBody) {
        return new ResponseEntity<VetsDto>(service.createNewVet(requestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<VetsDto> updateVet(@RequestBody VetsDto updateRequest) {
        if (updateRequest.getId() == null || updateRequest.getId() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(updateRequest);
        }
        return ResponseEntity.ok(service.updateVet(updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVet(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
