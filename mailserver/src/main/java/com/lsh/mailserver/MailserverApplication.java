package com.lsh.mailserver;

import com.lsh.vhr.model.MailConstants;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MailserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailserverApplication.class, args);
    }

    @Bean
    Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
