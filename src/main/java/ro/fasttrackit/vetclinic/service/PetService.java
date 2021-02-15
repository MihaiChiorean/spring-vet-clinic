package ro.fasttrackit.vetclinic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.fasttrackit.vetclinic.model.PetDto;
import ro.fasttrackit.vetclinic.model.entity.PetEntity;
import ro.fasttrackit.vetclinic.repository.PetRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {
    @Value("${server.port}") // SpEL (Spring Expression Language)
    private String serverPort;

    private final PetRepository repository;

    public PetService(PetRepository injectedRepository) {
        this.repository = injectedRepository;
    }

    public PetDto createNewPet(PetDto request) {
        PetEntity newPet = new PetEntity();
        newPet.setId(request.getId());
        newPet.setName(request.getName());
        newPet.setSpecies(request.getSpecies());
        PetEntity savedEntity = this.repository.save(newPet);

        return mapEntityToPetResponse(savedEntity);
    };

    public PetDto updatePet(PetDto request) {
        PetEntity updatedPet = new PetEntity();
        updatedPet.setSpecies(request.getSpecies());
        updatedPet.setName(request.getName());
        updatedPet.setId(request.getId());
        repository.save(updatedPet);

        PetEntity savedEntity = this.repository.save(updatedPet);
        return mapEntityToPetResponse(savedEntity);
    }

    public List<PetDto> getAll() {
        return this.repository.findAll()
                .stream()
                .map(entity -> mapEntityToPetResponse(entity))
                .collect(Collectors.toList());
    }

    public PetDto getById(Long petId) {
        return this.repository.findById(petId)
                .map(entity -> mapEntityToPetResponse(entity))
                .get();
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    public PetDto mapEntityToPetResponse(PetEntity petEntity) {
        PetDto responseObject = new PetDto();
        responseObject.setId(petEntity.getId());
        responseObject.setName(petEntity.getName());
        responseObject.setSpecies(petEntity.getSpecies());
        return responseObject;
    }
}
