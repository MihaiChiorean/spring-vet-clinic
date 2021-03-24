package ro.fasttrackit.vetclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.vetclinic.model.ConsultationDto;
import ro.fasttrackit.vetclinic.service.ConsultationService;

import java.util.List;

@RestController
@RequestMapping("api/consultation")
public class ConsultationController {

    private final ConsultationService service;

    public ConsultationController(ConsultationService injectedServ) {
        this.service = injectedServ;

    }

    @GetMapping("/{id}")
    public ConsultationDto getConsultationById(@PathVariable(name = "id") Long consultId) {
        return this.service.getById(consultId);
    }

    @GetMapping("/all")
    public List<ConsultationDto> getAllConsultations() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<ConsultationDto> createNewConsult(@RequestBody ConsultationDto consultationDtoRequest) {
        return ResponseEntity.ok(service.createNewConsultation(consultationDtoRequest));
    }

    @PutMapping
    public ResponseEntity<ConsultationDto> updateConsultation(@RequestBody ConsultationDto updateRequest) {
        if (updateRequest.getId() == null || updateRequest.getId() <= 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(updateRequest);
        }
        return ResponseEntity.ok(service.updateConsultation(updateRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteConsultation(@PathVariable(name = "id") Long idToDelete) {
        this.service.deleteById(idToDelete);
    }
}
