package com.lsh.vhr.task;

import com.lsh.vhr.model.Employee;
import com.lsh.vhr.model.MailConstants;
import com.lsh.vhr.model.MailSendLog;
import com.lsh.vhr.service.EmployeeService;
import com.lsh.vhr.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MailSendTask {
    @Autowired
    MailSendLogService mailSendLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    EmployeeService employeeService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailResendTask() {
        List<MailSendLog> logs = mailSendLogService.getMailSendLogsByStatus();
        for (MailSendLog log : logs) {
            if (log.getCount() >= 3) {
                mailSendLogService.updateMailSendLogStatus(log.getMsgId(), 2);//直接设置失败
            } else {
                mailSendLogService.updateCount(log.getMsgId(), new Date());
                Employee emp = employeeService.getEmployeeById(log.getEmpId());
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_ROUTING_KEY_NAME
                        , emp, new CorrelationData(log.getMsgId()));

            }
        }
    }

}
