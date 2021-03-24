package ro.fasttrackit.vetclinic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ro.fasttrackit.vetclinic.model.DiagnosisDto;
import ro.fasttrackit.vetclinic.model.messaging.DiagnosisMessageDto;
import ro.fasttrackit.vetclinic.model.entity.DiagnosisEntity;
import ro.fasttrackit.vetclinic.repository.*;

@Service
public class DiagnosisService {

    @Value("${server.port}")
    private String serverPort;

    private final DiagnosisRepository repository;
    private final ConsultationRepository consultationRepository;
    private final RabbitTemplate rabbitMqTemplate;
    private final DirectExchange directExchange;

    public DiagnosisService(DiagnosisRepository repository,ConsultationRepository consultationRepository,RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.repository = repository;
        this.consultationRepository = consultationRepository;
        this.rabbitMqTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    //post
    public DiagnosisDto createNewDiagnosis(DiagnosisDto request){
        DiagnosisEntity newDiagnosis = new DiagnosisEntity();
        newDiagnosis.setConsultation(consultationRepository.findById(request.getConsultationId()).get());
        newDiagnosis.setTitle(request.getTitle());
        newDiagnosis.setDescription(request.getDescription());
        newDiagnosis.setRecommendations(request.getRecommendations());

        DiagnosisEntity saveEntity = this.repository.save(newDiagnosis);

        DiagnosisMessageDto diagnosisCreatedMessage = new DiagnosisMessageDto();
        diagnosisCreatedMessage.setConsultationId(newDiagnosis.getConsultation().getId());
        diagnosisCreatedMessage.setTitle(newDiagnosis.getTitle());
        diagnosisCreatedMessage.setDescription(newDiagnosis.getDescription());
        diagnosisCreatedMessage.setRecommendations(newDiagnosis.getRecommendations());

        ObjectMapper objectMapper = new ObjectMapper();

        try{
            String stringMessageConverted = objectMapper.writeValueAsString(diagnosisCreatedMessage);
            rabbitMqTemplate.convertAndSend(directExchange.getName(), "diagnosis", stringMessageConverted);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
        }
        return mapEntityToDiagnosisResponse(saveEntity);
    }

    public DiagnosisDto mapEntityToDiagnosisResponse(DiagnosisEntity entity){
        DiagnosisDto response = new DiagnosisDto();
        response.setId(entity.getId());
        response.setConsultationId(entity.getConsultation().getId());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setRecommendations(entity.getRecommendations());
        return response;
    }
}
