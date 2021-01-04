package ro.fasttrackit.vetclinic.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.vetclinic.model.Pet;
import ro.fasttrackit.vetclinic.model.entity.PetEntity;
import ro.fasttrackit.vetclinic.repository.PetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository injectedRepository) {
        this.repository = injectedRepository;
    }

    public Pet createNewOrUpdatePet(Pet petRequest) {
        if(petRequest.getId() == null) {

            PetEntity newPet = new PetEntity();
            newPet.setName(petRequest.getName());
            newPet.setSpecies(petRequest.getSpecies());
            PetEntity savedEntity = this.repository.save(newPet);

            return convertPetEntityToPet(savedEntity);
        }
        Optional<PetEntity> foundPetById = repository.findById(petRequest.getId());
        if(foundPetById.isPresent()) {
            PetEntity updatedPet = new PetEntity();
            updatedPet.setSpecies(petRequest.getSpecies());
            updatedPet.setName(petRequest.getName());
            updatedPet.setId(petRequest.getId());
            repository.save(updatedPet);

            PetEntity savedEntity = this.repository.save(updatedPet);
            return convertPetEntityToPet(savedEntity);
        }
        return null;
    }

    public Pet convertPetEntityToPet (PetEntity petEntity) {
        Pet responseObject = new Pet();
        responseObject.setId(petEntity.getId());
        responseObject.setName(petEntity.getName());
        responseObject.setSpecies(petEntity.getSpecies());
        return responseObject;
    }

    public List<PetEntity> getAll() {
        List<PetEntity> getPetList = this.repository.findAll();
        return getPetList;
    }

    public Optional<PetEntity> getById(Long id) {
        Optional<PetEntity> getPetById = this.repository.findById(id);
        return getPetById;
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }
}
