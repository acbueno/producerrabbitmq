package br.com.acbueno.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import br.com.acbueno.producer.model.Employee;

@RestController
@RequestMapping(value = "/api")
public class RabbitMQWebController {

  
  @Autowired 
  private RabbitTemplate rabbittempalte;
  
  //@Autowired
  //private AmqpTemplate amqpTemplate;

  @Autowired
  private Binding binding;

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQWebController.class);

  @GetMapping(value = "/send/{empName}/{empId}/{salary}")
  @ResponseStatus(code = HttpStatus.OK)
  public String producer(@PathVariable("empName") String empName,
      @PathVariable("empId") String empId, @PathVariable("salary") int salary) {

    Employee emp = new Employee();
    emp.setEmpId(empId);
    emp.setEmpName(empName);
    emp.setSalary(salary);

    LOGGER.info("Value message: " + emp.toString());

    rabbittempalte.convertAndSend(binding.getExchange(), binding.getRoutingKey(), emp);

    return "Message sent sucessfully to the queue";
  }

}
