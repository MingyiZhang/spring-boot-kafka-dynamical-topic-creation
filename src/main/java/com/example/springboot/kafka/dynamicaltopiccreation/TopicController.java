package com.example.springboot.kafka.dynamicaltopiccreation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("topics")
@RestController
public class TopicController {

  @Autowired
  private AdminClient adminClient;

  @GetMapping("/getAll")
  public List<String> getAllTopics() {
    try {
      ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
      listTopicsOptions.listInternal(false);
      return new ArrayList<>(adminClient.listTopics(listTopicsOptions).names().get());
    } catch (InterruptedException | ExecutionException e) {
      return Collections.singletonList(e.getMessage());
    }
  }

  @GetMapping("/get")
  public String getTopic(@RequestParam("name") String name) {
    try {
      return adminClient.describeTopics(Collections.singleton(name)).all().get().get(name).toString();
    } catch (InterruptedException | ExecutionException e) {
      return e.getMessage();
    }
  }

  @PostMapping("/createTopics")
  public String createTopics(@RequestParam("names") List<String> names) {
    List<NewTopic> topics = names.stream()
        .map(name -> TopicBuilder.name(name).partitions(1).replicas(1).compact().build())
        .collect(Collectors.toList());
    CreateTopicsResult createTopicsResult = adminClient.createTopics(topics);
    try {
      System.out.println(createTopicsResult.all().get());
      return "OK";
    } catch (InterruptedException | ExecutionException e) {
      return e.getMessage();
    }
  }

  @PostMapping("/create")
  public String createTopic(
      @RequestParam("name") String name,
      @RequestParam(value = "partitions", defaultValue = "1") Integer partitions,
      @RequestParam(value = "replicas", defaultValue = "1") Integer replicas,
      @RequestParam(value = "compact", defaultValue = "false") Boolean compact
  ) {
    TopicBuilder builder = TopicBuilder.name(name).partitions(partitions).replicas(replicas);
    if (compact) {
      builder.compact();
    }
    adminClient.createTopics(Collections.singleton(builder.build()));
    return "OK";
  }

  @DeleteMapping("/delete")
  public String deleteTopic(
      @RequestParam("name") String name
  ) {
    DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Collections.singleton(name));
    try {
      System.out.println(deleteTopicsResult.all().get());
    } catch (InterruptedException | ExecutionException e) {
      return e.getMessage();
    }
    return "OK";
  }

  @PostMapping("/test")
  public String test(
      @RequestParam("param1") String param1,
      @RequestParam("param2") String param2
  ) {
    return param1 + " | " + param2;
  }

}
