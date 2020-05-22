import sbt.Package._
import sbt._

scalaVersion := "2.13.1"

resolvers ++= Seq(
  "Confluent OSS" at "https://packages.confluent.io/maven/",
  "Jitpack" at "https://jitpack.io"
)
enablePlugins(DockerPlugin)
//exposedPorts := Seq(8666)

libraryDependencies ++= Vector(
  Library.vertx_lang_scala,
  Library.vertx_web,
  Library.scalaTest % "test",
  Library.vertx_kafka_client,
  Library.vertx_redis_client_scala,
  Library.vertx_config_kubernetes_configmap,
  Library.vertx_config
)
libraryDependencies += "io.confluent" % "kafka-avro-serializer" % "5.5.0"
libraryDependencies += "io.github.embeddedkafka" %% "embedded-kafka-schema-registry" % "5.5.0.1" % Test

packageOptions += ManifestAttributes(
  ("Main-Verticle", "scala:RefDataKafkaVerticle"))

