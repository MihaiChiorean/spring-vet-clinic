package ro.fasttrackit.vetclinic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.fasttrackit.vetclinic.model.ConsultationDto;
import ro.fasttrackit.vetclinic.model.entity.ConsultationEntity;
import ro.fasttrackit.vetclinic.model.messaging.ConsultationMessageDto;
import ro.fasttrackit.vetclinic.repository.ConsultationRepository;
import ro.fasttrackit.vetclinic.repository.OwnerRepository;
import ro.fasttrackit.vetclinic.repository.PetRepository;
import ro.fasttrackit.vetclinic.repository.VetsRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

@Service
public class ConsultationService {
    @Value("${server.port}")
    private String serverPort;

    private final ConsultationRepository repository;
    private final PetRepository petRepository;
    private final VetsRepository vetRepository;
    private final OwnerRepository ownerRepository;
    private final RabbitTemplate rabbitMqTemplate;
    private final DirectExchange directExchange;

    public ConsultationService(ConsultationRepository injectedRepo, PetRepository injectedPetRepository, VetsRepository injectedVetRepository, OwnerRepository injectedOwnerRepository, RabbitTemplate template, DirectExchange directExchange) {
        this.repository = injectedRepo;
        this.petRepository = injectedPetRepository;
        this.vetRepository = injectedVetRepository;
        this.ownerRepository = injectedOwnerRepository;
        this.rabbitMqTemplate = template;
        this.directExchange = directExchange;

    }

    public ConsultationDto createNewConsultation(ConsultationDto request) {
        ConsultationEntity newConsultation = new ConsultationEntity();
        Optional<ConsultationEntity> optionalConsultation = repository.findConsultationByOwnerAndPet(request.getOwnerId(),request.getPetId());
        if(optionalConsultation.isPresent()){
            newConsultation = optionalConsultation.get();
        }else {
            newConsultation.setPet(petRepository.findById(request.getPetId()).get());
            newConsultation.setOwner(ownerRepository.findById(request.getOwnerId()).get());
        }

        newConsultation.setVet(vetRepository.findById(request.getVetId()).get());
        ConsultationEntity saveEntity = this.repository.save(newConsultation);

        ConsultationMessageDto consultationCreatedMessage = new ConsultationMessageDto();
        consultationCreatedMessage.setVetName(newConsultation.getVet().getFirstName() + " " + newConsultation.getVet().getLastName());
        consultationCreatedMessage.setPetName(newConsultation.getPet().getName());
        consultationCreatedMessage.setOwnerName(newConsultation.getOwner().getFirstName() + " " + newConsultation.getOwner().getLastName());


        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String stringMessageConverted = objectMapper.writeValueAsString(consultationCreatedMessage);
            rabbitMqTemplate.convertAndSend(directExchange.getName(), "consultations", stringMessageConverted);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
        }
        return mapEntityToConsultationResponse(saveEntity);
    }

    public ConsultationDto updateConsultation(ConsultationDto requested) {
        ConsultationEntity consultationUpdate = new ConsultationEntity();
        consultationUpdate.setId(requested.getId());
        consultationUpdate.setVet(vetRepository.findById(requested.getVetId()).get());
        consultationUpdate.setPet(petRepository.findById(requested.getPetId()).get());
        consultationUpdate.setOwner(ownerRepository.findById(requested.getOwnerId()).get());

        ConsultationEntity updatedConsult = this.repository.save(consultationUpdate);
        return mapEntityToConsultationResponse(updatedConsult);
    }

    public ConsultationDto getById(Long consultationId) {
        return this.repository.findById(consultationId)
                .map(entity -> mapEntityToConsultationResponse(entity))
                .get();
    }

    public List<ConsultationDto> getAll() {
        return this.repository.findAll()
                .stream()
                .map(entity -> mapEntityToConsultationResponse(entity))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    private ConsultationDto mapEntityToConsultationResponse(ConsultationEntity consultationEntity) {
        ConsultationDto response = new ConsultationDto();
        response.setId(consultationEntity.getId());
        response.setVetId(consultationEntity.getVet().getId());
        response.setOwnerId(consultationEntity.getOwner().getId());
        response.setPetId(consultationEntity.getPet().getId());
        return response;
    }
}
