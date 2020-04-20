package com.lsh.mailserver.receiver;

import com.lsh.vhr.model.Employee;
import com.lsh.vhr.model.Hr;
import com.lsh.vhr.model.MailConstants;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

@Component
public class MailReceiver {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public static final Logger logger=LoggerFactory.getLogger(MailReceiver.class);

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) throws IOException {
        Employee employee = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");
        if (stringRedisTemplate.opsForHash().entries("mail_log").containsKey("msgId")) {
            //redis包含该key，说明该消息被消费过
            logger.info(msgId+":消息已经被消费了");
            channel.basicAck(tag,false);//确认消息已经消费
            return;
        }


        logger.info(employee.toString());
        //收到消息，发送邮件
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        try {
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setSubject("入职欢迎");
            helper.setSentDate(new Date());

            Context context=new Context();
            context.setVariable("nickname",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("joblevelName",employee.getJobLevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());

            String mail = templateEngine.process("mail.html", context);
            helper.setText(mail,true);

            javaMailSender.send(msg);

            stringRedisTemplate.opsForHash().put("mail_log",msgId,"lsh");
            channel.basicAck(tag,false);//确认消息已经消费
            logger.info(msgId+":邮件发送成功");
        } catch (MessagingException e) {
            channel.basicNack(tag,false,true);
            logger.error("邮件发送失败："+e.getMessage());
        }

    }

}
