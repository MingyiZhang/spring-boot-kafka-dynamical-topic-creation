# Create and Delete Kafka Topics Using Java and Spring Kafka

A demo shows how to create, delete and get Kafka topics.

## Setup
### local
In `application.yml`, set `spring.profiles.active` to `local`.

### confluent cloud
In `application.yml`, set `spring.profiles.active` to `cloud`.
Rename `application-cloud-example.yml` to `application-cloud.yml`.
Replace `<CCLOUD_CLUSTER_ADDRESS>`, `<CCLOUD_API_KEY>` and `CCLOUD_API_SECRET` to your Confluent Cloud
Cluster IP, API key and secret.

## Run
```shell script
./gradlew bootRun
```

### Get
```shell script
curl -X GET localhost:8080/topics/getAll
```

```shell script
curl -X GET "localhost:8080/topics/get?name=<TOPIC_NAME>"
```

### POST
```shell script
curl -X POST "localhost:8080/topics/createTopics?names=<TOPIC1>,<TOPIC2>,..."
```
```shell script
curl -X POST "localhost:8080/topics/create?name=<TOPIC_NAME>&partitions=<PARTITIONS>&replicas=<REPLICAS>&compact=<true or false>"
```

### DELETE
```shell script
curl -X POST "localhost:8080/topics/delete?name=<TOPIC_NAME>"
```