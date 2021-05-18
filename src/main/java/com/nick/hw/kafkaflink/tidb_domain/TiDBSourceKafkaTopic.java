package com.nick.hw.kafkaflink.tidb_domain;

import javax.persistence.*;

@Entity
@Table(name="source_kafka_topic")
public class TiDBSourceKafkaTopic {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        return "TiDBSourceKafkaTopic{" +
                "id=" + id +
                ", topicName='" + topicName + '\'' +
                ", srcPayload='" + srcPayload + '\'' +
                '}';
    }
}
