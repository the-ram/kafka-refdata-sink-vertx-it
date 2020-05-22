package com.theram.it.kafka

import java.util.logging.{Level, Logger}

import com.theram.it.Constants
import com.theram.it.model.ReferenceDataHolder
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.vertx.core.json.JsonObject
import io.vertx.kafka.client.consumer.{KafkaConsumer, KafkaConsumerRecord}
import io.vertx.lang.scala.ScalaVerticle
import io.vertx.scala.kafka.client.KafkaConsumerScala
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.consumer.ConsumerConfig

import scala.jdk.CollectionConverters.MapHasAsJava

class RefDataKafkaVerticle extends ScalaVerticle {
  private val LOGGER = Logger.getLogger(getClass.getName)

  private val KAFKA_BOOTSTRAP_ENDPOINT = "kafka-bootstrap-server"
  private val DEFAULT_KAFKA_BOOTSTRAP_ENDPOINT = "localhost:9092"

  private val KAFKA_AUTO_OFFSET_RESET = "kafka-offset-reset"
  private val DEFAULT_KAFKA_AUTO_OFFSET_RESET = "earliest"

  private val KAFKA_SCHEMA_REGISTRY_URL = "kafka-schema-registry-url"
  private val DEFAULT_KAFKA_SCHEMA_REGISTRY_URL = "http://localhost:8081"

  private val KAFKA_ENABLE_AUTO_COMMIT = "kafka-commit-offset"
  private val DEFAULT_KAFKA_ENABLE_AUTO_COMMIT = "true"

  private val KAFKA_CONSUMER_GROUP_ID = "kafka-consumer-group"
  private val DEFAULT_KAFKA_CONSUMER_GROUP_ID = "kafka-refdata-sync"

  private val KAFKA_KEY_DESERIALIZER_CLASS = "kafka-key-deserializer"
  private val DEFAULT_KAFKA_KEY_DESERIALIZER_CLASS =
    "io.confluent.kafka.serializers.KafkaAvroDeserializer"

  private val KAFKA_VALUE_DESERIALIZER_CLASS = "kafka-value-deserializer"
  private val DEFAULT_VALUE_KEY_DESERIALIZER_CLASS =
    "io.confluent.kafka.serializers.KafkaAvroDeserializer"

  override def start(): Unit = {
    val propertyConfig: JsonObject = vertx
      .getOrCreateContext()
      .config()
    val configuration = Map(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> propertyConfig
        .getString(KAFKA_BOOTSTRAP_ENDPOINT, DEFAULT_KAFKA_BOOTSTRAP_ENDPOINT),
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> propertyConfig
        .getString(KAFKA_KEY_DESERIALIZER_CLASS,
                   DEFAULT_KAFKA_KEY_DESERIALIZER_CLASS),
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> propertyConfig
        .getString(KAFKA_VALUE_DESERIALIZER_CLASS,
                   DEFAULT_VALUE_KEY_DESERIALIZER_CLASS),
      ConsumerConfig.GROUP_ID_CONFIG -> propertyConfig
        .getString(KAFKA_CONSUMER_GROUP_ID, DEFAULT_KAFKA_CONSUMER_GROUP_ID),
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG -> propertyConfig
        .getString(KAFKA_AUTO_OFFSET_RESET, DEFAULT_KAFKA_AUTO_OFFSET_RESET),
      ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG -> propertyConfig
        .getString(KAFKA_ENABLE_AUTO_COMMIT, DEFAULT_KAFKA_ENABLE_AUTO_COMMIT),
      AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG ->
        propertyConfig.getString(KAFKA_SCHEMA_REGISTRY_URL,
                                 DEFAULT_KAFKA_SCHEMA_REGISTRY_URL)
    )
    // use consumer for interacting with Apache Kafka
    val consumer: KafkaConsumer[GenericData.Record, GenericData.Record] =
      KafkaConsumer.create(vertx, configuration.asJava)
    consumer.handler(recordHandler)
    consumer.subscribeFuture(Constants.SPECIAL_TOPIC)
    consumer.exceptionHandler(handlerException)
  }

  private def recordHandler(
      record: KafkaConsumerRecord[GenericData.Record, GenericData.Record])
    : Unit = {
    val topicNameParts = record.topic().split("_")
    if (topicNameParts.length < 6) {
      LOGGER.log(Level.SEVERE,
                 "Kafka topic not as per expected format {0} at timestamp {1} ",
                 Array(record.topic(), record.timestamp()))
      return
    }
    val collectionName: String = topicNameParts(5)
    val referenceDataHolder = ReferenceDataHolder(collectionName,
                                                  record.key().toString,
                                                  record.key().toString)
    vertx
      .eventBus()
      .publish(Constants.REF_DATA_PUBLISH_ADDRESS, referenceDataHolder)
  }

  private def handlerException(exception: Throwable): Unit = {
    LOGGER.log(Level.SEVERE,
               "Exception while reading message from Kafka",
               exception)
  }
}
