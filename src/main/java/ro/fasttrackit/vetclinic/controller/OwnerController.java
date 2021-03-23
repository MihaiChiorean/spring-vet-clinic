package ro.fasttrackit.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.vetclinic.model.OwnerDto;
import ro.fasttrackit.vetclinic.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("api/owner")
public class OwnerController {

    private final OwnerService service;

    public OwnerController(OwnerService injectedService) {
        this.service = injectedService;
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable(name = "id") Long id) {
        return service.getById(id);
    };

    @GetMapping("/all")
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        return ResponseEntity.ok(service.getAll());
    };

    @PostMapping
    public ResponseEntity<OwnerDto> createNewOwner(@RequestBody OwnerDto requestBody) {
        return new ResponseEntity<OwnerDto>(service.createNewOwner(requestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OwnerDto> updateOwner(@RequestBody OwnerDto updateRequest) {
        if (updateRequest.getId() == null || updateRequest.getId() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(updateRequest);
        }
        return ResponseEntity.ok(service.updateOwner(updateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
