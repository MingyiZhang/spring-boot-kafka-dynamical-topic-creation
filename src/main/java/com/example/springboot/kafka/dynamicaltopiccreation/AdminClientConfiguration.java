package com.example.springboot.kafka.dynamicaltopiccreation;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

@Service
public class AdminClientConfiguration {

  @Autowired
  private KafkaAdmin kafkaAdmin;

  @Bean
  public AdminClient createAdminClient() {
    return AdminClient.create(kafkaAdmin.getConfig());
  }
}
