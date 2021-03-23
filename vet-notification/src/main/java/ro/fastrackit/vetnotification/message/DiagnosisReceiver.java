package ro.fastrackit.vetnotification.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.fastrackit.vetnotification.model.DiagnosisMessageReceiver;

import java.time.Instant;
import java.util.Date;

@Component
@RabbitListener(queues = "diagnosis")
public class DiagnosisReceiver {

    @RabbitHandler
    public void receiveDiagnosis(String diagnosisMessage){
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            DiagnosisMessageReceiver messageReceiverDiagnosis = objectMapper.readValue(diagnosisMessage, DiagnosisMessageReceiver.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Diagnosis:  " + diagnosisMessage + "sent on:" + "[" + Date.from(Instant.now()) + "]");
    }
}
