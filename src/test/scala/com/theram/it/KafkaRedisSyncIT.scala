package com.theram.it

import java.util.logging.{Level, Logger}

import com.theram.it.kafka.RefDataKafkaVerticle
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig
import io.vertx.core.json.JsonObject
import io.vertx.kafka.client.producer.{KafkaProducer, KafkaProducerRecord}
import net.manub.embeddedkafka.schemaregistry.{EmbeddedKafka, EmbeddedKafkaConfig}
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericData.Record
import org.apache.kafka.clients.producer.ProducerConfig
import org.scalatest.Matchers

import scala.concurrent.Promise
import scala.jdk.CollectionConverters.MapHasAsJava


class KafkaRedisSyncIT extends VerticleTesting[RefDataKafkaVerticle] with Matchers with EmbeddedKafka {
  private val LOGGER = Logger.getLogger(getClass.getName)
  private val KAFKA_BOOTSTRAP_ENDPOINT = "kafka-bootstrap-server"
  private val DEFAULT_KAFKA_BOOTSTRAP_ENDPOINT = "localhost:6001"
  private val KAFKA_SCHEMA_REGISTRY_URL = "kafka-schema-registry-url"
  private val DEFAULT_KAFKA_SCHEMA_REGISTRY_URL = "http://localhost:6002"

  "RefDataKafkaVerticle" should "start kafka and sink a message" in {
    implicit val kafkaEmbeddedConfig: EmbeddedKafkaConfig = EmbeddedKafkaConfig(zooKeeperPort = 6000, kafkaPort = 6001 , schemaRegistryPort = 6002)
    kafkaEmbeddedConfig.customSchemaRegistryProperties
    val propertyConfig: JsonObject = vertx
      .getOrCreateContext()
      .config()
    LOGGER.log(Level.INFO, "**Getting connected to {0}" , propertyConfig
      .getString(KAFKA_BOOTSTRAP_ENDPOINT, DEFAULT_KAFKA_BOOTSTRAP_ENDPOINT))
    val configuration = Map(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> propertyConfig
        .getString(KAFKA_BOOTSTRAP_ENDPOINT, DEFAULT_KAFKA_BOOTSTRAP_ENDPOINT),
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroSerializer",
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG -> "io.confluent.kafka.serializers.KafkaAvroSerializer",
      ProducerConfig.ACKS_CONFIG -> "1",
      AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG ->
        propertyConfig.getString(KAFKA_SCHEMA_REGISTRY_URL,
          DEFAULT_KAFKA_SCHEMA_REGISTRY_URL)
    )

    withRunningKafka {
      val kafkaProducer: KafkaProducer[GenericData.Record, GenericData.Record] =
        KafkaProducer.createShared(vertx, "the-producer", configuration.asJava)
      LOGGER.log(Level.INFO, "**Now connecting to producer {0}" , kafkaProducer)

      val cameraValue = createCameraValue()
      val keyValue = createCameraKey()
      LOGGER.log(Level.INFO, "Sending Key {0}", keyValue.toString)
      val kafkaProducerRecord: KafkaProducerRecord[GenericData.Record, GenericData.Record] =
        KafkaProducerRecord.create(Constants.SPECIAL_TOPIC, keyValue, cameraValue)
      LOGGER.log(Level.INFO, "KafkaProducerRecord {0}", kafkaProducerRecord.topic())
      val recordMetaData = kafkaProducer.send(kafkaProducerRecord)
      LOGGER.log(Level.INFO, "Metadata  {0}", recordMetaData)
      val promise = Promise[String]
      recordMetaData.onComplete({ handler => {
        if (handler.failed()) {
          handler.cause().printStackTrace()
          LOGGER.log(Level.SEVERE, "The End {0}", handler.cause())
          promise.failure(handler.cause())
        } else {
          LOGGER.log(Level.INFO, "Data published {0}", recordMetaData.result())
          promise.success(recordMetaData.result().getTopic)
        }
      }})
      promise.future.map(res => res should equal("sd_int_kafka_k1_evt_camera_avro_v0r1"))
    }
  }

  def createCameraKey(): Record = {
    val keyRecord = new GenericData.Record(smol.SCHEMA$)
    keyRecord.put("name", "latency")
    keyRecord
  }


  def createCameraValue(): Record = {
    val currentTime = System.currentTimeMillis()
    val valueRecord = new GenericData.Record(smol.SCHEMA$)
    valueRecord.put("unit", "second")
    valueRecord
  }
}
