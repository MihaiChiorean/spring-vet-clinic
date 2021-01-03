package ro.fasttrackit.vetclinic.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.vetclinic.model.Pet;
import ro.fasttrackit.vetclinic.model.entity.PetEntity;
import ro.fasttrackit.vetclinic.repository.PetRepository;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository injectedRepository) {
        this.repository = injectedRepository;
    }

    public Pet createNewPet(Pet petRequest) {
        PetEntity newPet = new PetEntity();
        newPet.setName(petRequest.getName());
        newPet.setSpecies(petRequest.getSpecies());
        PetEntity savedEntity = this.repository.save(newPet);

        Pet responseObject = new Pet();
        responseObject.setId(savedEntity.getId());
        responseObject.setName(savedEntity.getName());
        responseObject.setSpecies(savedEntity.getSpecies());
        return responseObject;
    }
}
