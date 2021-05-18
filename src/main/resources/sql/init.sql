-- create a table in Postgres for storing the original messages from Kafka streaming
CREATE TABLE source_kafka_topic
   (ID DECIMAL(19,0) NOT NULL,
    TOPIC_NAME VARCHAR(255),
    SRC_PAYLOAD VARCHAR(4000)
   )
  ;
ALTER TABLE source_kafka_topic ADD CONSTRAINT pk_source_kafka_topic_id PRIMARY KEY(ID) ;

CREATE SEQUENCE hibernate_sequence MINVALUE 1 /* Warning: MAXVALUE 9999999999999999999999999999 */ INCREMENT BY 1 START WITH 1 CACHE 20  NO CYCLE;


-- create a table in TiDB
CREATE TABLE source_kafka_topic
   (ID int  auto_increment,
    TOPIC_NAME VARCHAR(255),
    SRC_PAYLOAD VARCHAR(4000),
    PRIMARY KEY (ID)
   )
  ;
CREATE SEQUENCE hibernate_sequence MINVALUE 1 /* Warning: MAXVALUE 9999999999999999999999999999 */ INCREMENT BY 1 START WITH 1 CACHE 20  NO CYCLE;
