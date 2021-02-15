package ro.fasttrackit.vetclinic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.fasttrackit.vetclinic.model.OwnerDto;
import ro.fasttrackit.vetclinic.model.entity.OwnerEntity;
import ro.fasttrackit.vetclinic.repository.OwnerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    @Value("${server.port}") // SpEL (Spring Expression Language)
    private String serverPort;

    private final OwnerRepository repository;

    public OwnerService(OwnerRepository injectedRepository) {
        this.repository = injectedRepository;
    }
    public OwnerDto createNewOwner(OwnerDto request) {
        OwnerEntity newOwner = new OwnerEntity();
        newOwner.setId(request.getId());
        newOwner.setFirstName(request.getFirstName());
        newOwner.setLastName(request.getLastName());
        newOwner.setCnp(request.getCnp());
        newOwner.setPhoneNumber(request.getPhoneNumber());
        newOwner.setEmail(request.getEmail());
        OwnerEntity savedEntity = this.repository.save(newOwner);

        return mapEntityToOwnerResponse(savedEntity);
    };

    public OwnerDto updateOwner(OwnerDto request) {
        OwnerEntity updatedOwner = new OwnerEntity();
        updatedOwner.setId(request.getId());
        updatedOwner.setFirstName(request.getFirstName());
        updatedOwner.setLastName(request.getLastName());
        updatedOwner.setCnp(request.getCnp());
        updatedOwner.setPhoneNumber(request.getPhoneNumber());
        updatedOwner.setEmail(request.getEmail());
        repository.save(updatedOwner);

        OwnerEntity savedEntity = this.repository.save(updatedOwner);
        return mapEntityToOwnerResponse(savedEntity);
    }

    public List<OwnerDto> getAll() {
        return this.repository.findAll()
                .stream()
                .map(entity -> mapEntityToOwnerResponse(entity))
                .collect(Collectors.toList());
    }

    public OwnerDto getById(Long OwnerId) {
        return this.repository.findById(OwnerId)
                .map(entity -> mapEntityToOwnerResponse(entity))
                .get();
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    public OwnerDto mapEntityToOwnerResponse(OwnerEntity ownerEntity) {
        OwnerDto responseObject = new OwnerDto();
        responseObject.setId(ownerEntity.getId());
        responseObject.setFirstName(ownerEntity.getFirstName());
        responseObject.setLastName(ownerEntity.getLastName());
        responseObject.setCnp(ownerEntity.getCnp());
        responseObject.setPhoneNumber(ownerEntity.getPhoneNumber());
        responseObject.setEmail(ownerEntity.getEmail());
        return responseObject;
    }
}
