#Technical skillsets and Components:
1. Java8 + Spring Boot + JPA: automatic configuraiton and startup, ORMapping and database access etc.
2. Camel: integration with upstreams and downstreams, based on event-driven archiecture it set up an event processing pipelne for Java application.
3. Debezium: it works as a binlog connector for Mysql Changed Data Capture(CDC), changes CDC to events as Json formatted to send to Kafka.
4. Kafka/Zookeeper: it publishes topics to listen inbound CDC events from Debezium Connector.
5. PostgreSQL: DB stores metadata and payloads from CDC events for further analysis.
6. Flink: Streaming compute in memory based on Kafka topics outbound messages.

#Below is a guideline for introducing the env setup based on docker
#Refer to the page: @https://debezium.io/documentation/reference/tutorial.html#registering-connector-monitor-inventory-database

1. Dependencies of docker images, including kafka, zookeeper, mysql, postgres, kafka-connector and debezium.
debezium/kafka:1.5
debezium/zookeeper:1.5
debezium/example-mysql:1.
debezium/connect:1.5
postgres:12
flink:1.13.0-scala_2.11

2. Start a zookeeper to deploy Kafka:
 docker run -d -it --name zookeeper -p 12181:2181 -p 12888:2888 -p 13888:3888 debezium/zookeeper:1.5

3. Start a Kafka to provide application listeners to both container and outside of container(etc. local mac app).
 docker run -d -it --name kafka -p 29092:29092 \
 -e "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT" \
 -e "KAFKA_LISTENERS=PLAINTEXT://172.17.0.3:9092,PLAINTEXT_HOST://0.0.0.0:29092"  \
 -e "KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.17.0.3:9092,PLAINTEXT_HOST://localhost:29092"  \
 -e "KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT" \
 --link zookeeper:zookeeper debezium/kafka:1.5

4. Start a Mysql, ensure binlog enabled.
 docker run -d -it --name mysql -p 13306:3306 \
 -e MYSQL_ROOT_PASSWORD=admin \
 -e MYSQL_USER=user \
 -e MYSQL_PASSWORD=password \
 debezium/example-mysql:1.5

5. Start a Kafka Connect to monitor mysql CDC
 docker run -d -it --name connect -p 18083:8083 \
 -e GROUP_ID=1 \
 --link zookeeper:zookeeper \
 --link kafka:kafka \
 --link mysql:mysql \
 debezium/connect:1.5

6. Start a Postgres to store the original messages from Kafka
 docker run -d -p 5430:5432 --name demo_stream
 -e POSTGRES_PASSWORD=admin \
 -e POSTGRES_USER=admin \
 -e POSTGRES_DB=demo_stream \
 postgres:12

7. API Registering a debezium connector to monitor the "inventory" table on mysql, then route to a Kafka topic
 curl -i -X POST -H "Accept:application/json" -H
 '{
    "name": "test-inventory-connector",
    "config": {
      "connector.class": "io.debezium.connector.mysql.MySqlConnector",
      "tasks.max": "1",
      "database.hostname": "mysql",
      "database.port": "3306",
      "database.user": "root",
      "database.password": "admin",
      "database.server.id": "223344",
      "database.server.name": "fullfillment",
      "database.include.list": "inventory",
      "database.history.kafka.bootstrap.servers": "kafka:9092",
      "database.history.kafka.topic": "dbhistory.fullfillment"
    }
  }'

8. Check if Kafka connect is created or not
curl -H "Accept:application/json" localhost:18083/connectors/
-- ["test-inventory-connector"]

9. Check if Kafka topics are mapped well from Debezium(topic named as ${DBLogicName}.${MQSQL_DATABASE_NAME}.${MQSQL_TABLE_NAME})
    fullfillment
    fullfillment.inventory.addresses
    fullfillment.inventory.customers
    fullfillment.inventory.geom
    fullfillment.inventory.orders
    fullfillment.inventory.products
    ......


