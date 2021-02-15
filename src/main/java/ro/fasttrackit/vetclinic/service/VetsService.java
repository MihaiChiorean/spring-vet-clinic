package ro.fasttrackit.vetclinic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.fasttrackit.vetclinic.model.VetsDto;
import ro.fasttrackit.vetclinic.model.entity.VetsEntity;
import ro.fasttrackit.vetclinic.repository.VetsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VetsService {
    @Value("${server.port}") // SpEL (Spring Expression Language)
    private String serverPort;

    private final VetsRepository repository;

    public VetsService(VetsRepository injectedRepository) {
        this.repository = injectedRepository;
    }
    public VetsDto createNewVet(VetsDto request) {
        VetsEntity newVets = new VetsEntity();
        newVets.setId(request.getId());
        newVets.setFirstName(request.getFirstName());
        newVets.setLastName(request.getLastName());
        newVets.setCnp(request.getCnp());
        newVets.setYearOfGraduation(request.getYearOfGraduation());
        newVets.setSpecialization(request.getSpecialization());
        newVets.setPhoneNumber(request.getPhoneNumber());
        newVets.setEmail(request.getEmail());
        VetsEntity savedEntity = this.repository.save(newVets);

        return mapEntityToVetsResponse(savedEntity);
    }

    public VetsDto updateVet(VetsDto request) {
        VetsEntity updatedVets = new VetsEntity();
        updatedVets.setId(request.getId());
        updatedVets.setFirstName(request.getFirstName());
        updatedVets.setLastName(request.getLastName());
        updatedVets.setCnp(request.getCnp());
        updatedVets.setYearOfGraduation(request.getYearOfGraduation());
        updatedVets.setSpecialization(request.getSpecialization());
        updatedVets.setPhoneNumber(request.getPhoneNumber());
        updatedVets.setEmail(request.getEmail());
        repository.save(updatedVets);

        VetsEntity savedEntity = this.repository.save(updatedVets);
        return mapEntityToVetsResponse(savedEntity);
    }

    public List<VetsDto> getAll() {
        return this.repository.findAll()
                .stream()
                .map(entity -> mapEntityToVetsResponse(entity))
                .collect(Collectors.toList());
    }

    public VetsDto getById(Long VetsId) {
        return this.repository.findById(VetsId)
                .map(entity -> mapEntityToVetsResponse(entity))
                .get();
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    public VetsDto mapEntityToVetsResponse(VetsEntity vetsEntity) {
        VetsDto responseObject = new VetsDto();
        responseObject.setId(vetsEntity.getId());
        responseObject.setFirstName(vetsEntity.getFirstName());
        responseObject.setLastName(vetsEntity.getLastName());
        responseObject.setCnp(vetsEntity.getCnp());
        responseObject.setYearOfGraduation(vetsEntity.getYearOfGraduation());
        responseObject.setSpecialization(vetsEntity.getSpecialization());
        responseObject.setPhoneNumber(vetsEntity.getPhoneNumber());
        responseObject.setEmail(vetsEntity.getEmail());
        return responseObject;
    }
}
