package com.nick.hw.kafkaflink.domain;

import javax.persistence.*;

@Entity
@Table(name="source_kafka_topic")
public class SourceKafkaTopic {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOURCE_KAFKA_TOPIC_ID")
    @SequenceGenerator(name = "SEQ_SOURCE_KAFKA_TOPIC_ID", allocationSize = 1)
    private int id;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "src_payload")
    private String srcPayload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getSrcPayload() {
        return srcPayload;
    }

    public void setSrcPayload(String srcPayload) {
        this.srcPayload = srcPayload;
    }

    @Override public String toString() {
        return "SourceKafkaTopic{" +
                "id=" + id +
                ", topicName='" + topicName + '\'' +
                ", srcPayload='" + srcPayload + '\'' +
                '}';
    }
}
