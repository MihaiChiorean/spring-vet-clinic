package ro.fastrackit.vetnotification.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.fastrackit.vetnotification.model.ConsultationMessageReceiver;

import java.time.Instant;
import java.util.Date;

@Component
@RabbitListener(queues = "consultations")
public class ConsultationReceiver {


    @RabbitHandler
    public void receiveConsultation(String consultationMessage) {
        ObjectMapper objectMapper = new ObjectMapper();

        try{
            ConsultationMessageReceiver messageReceiverConsultation = objectMapper.readValue(consultationMessage, ConsultationMessageReceiver.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println( "Consultation: " + consultationMessage + "sent on:" + "[" + Date.from(Instant.now()) + "]");
    }
}
