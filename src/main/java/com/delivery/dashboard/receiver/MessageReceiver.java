package com.delivery.dashboard.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageReceiver {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    /**
     * RabbitMQ에서 메시지를 수신하여 처리하는 메서드.
     * 메시지 형식은 "주문 ID, 위도, 경도"이며 각 부분을 분리해 로깅합니다.
     *
     * @param message 수신된 메시지
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(String message) {
        try {
            log.info("Received message: " + message);

            String[] messageParts = message.split(",");
            int order_id = Integer.parseInt(messageParts[0].trim());
            double latitude = Double.parseDouble(messageParts[1].trim());
            double longitude = Double.parseDouble(messageParts[2].trim());

            log.info("Order ID: " + order_id);
            log.info("Latitude: " + latitude);
            log.info("Longitude: " + longitude);
        } catch (Exception e) {
            log.error("Error occurred while processing message: " + message, e);
        }
    }
}