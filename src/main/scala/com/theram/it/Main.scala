package com.theram.it

import java.util.logging.{Level, Logger}

import com.theram.it.kafka.RefDataKafkaVerticle
import com.theram.it.model.{ReferenceDataCodec, ReferenceDataHolder}
import io.vertx.config.{ConfigRetriever, ConfigRetrieverOptions, ConfigStoreOptions}
import io.vertx.core.json.JsonObject
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core._

object Main {
  private val LOGGER = Logger.getLogger(Main.getClass.getName)

  def main(args: Array[String]): Unit = {
    val vertx = Vertx.vertx()
    implicit val ec: VertxExecutionContext = VertxExecutionContext(
      vertx.getOrCreateContext())

    val retriever = ConfigRetriever.create(
      vertx,
      new ConfigRetrieverOptions()
        .addStore(
          new ConfigStoreOptions()
            .setType("file")
            .setConfig(new JsonObject().put("path", "sd-verticles.json")))
      //.addStore(new ConfigStoreOptions().setType("env"))
    )

    val kafkaVerticle = new RefDataKafkaVerticle()

    retriever
      .getConfig()
      .onComplete(handler => {
        /*
        vertx.deployVerticleFuture(kafkaVerticle).onComplete {
          case Success(_) => LOGGER.info("All verticles Started")
          case Failure(exception) => LOGGER.log(Level.SEVERE , "Exception in register and start of verticles" , exception)
        }
         */
        vertx.deployVerticle(kafkaVerticle)
        LOGGER
          .log(Level.INFO, "Configuration loaded {0} ", handler.result().getMap)
      })

    vertx
      .eventBus()
      .registerDefaultCodec(classOf[ReferenceDataHolder],
                            new ReferenceDataCodec())
    /*
      vertx.deployVerticleFuture(kafkaVerticle).onComplete {
        case Success(_) => LOGGER.info("All verticles Started")
        case Failure(exception) => LOGGER.log(Level.SEVERE , "Exception in register and start of verticles" , exception)
      }
   */
  }
}
