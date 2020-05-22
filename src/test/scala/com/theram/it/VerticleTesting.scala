package com.theram.it

import io.vertx.config.{ConfigRetriever, ConfigRetrieverOptions, ConfigStoreOptions}
import io.vertx.lang.scala.json.JsonObject
import io.vertx.lang.scala.{ScalaVerticle, VertxExecutionContext}
import io.vertx.scala.core._
import org.scalatest.{AsyncFlatSpec, BeforeAndAfter}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success}

abstract class VerticleTesting[A <: ScalaVerticle : TypeTag] extends AsyncFlatSpec with BeforeAndAfter {
  val vertx: Vertx = Vertx.vertx()
  implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
    vertx.getOrCreateContext()
  )
  private val retriever = ConfigRetriever.create(
    vertx, new ConfigRetrieverOptions().addStore(new ConfigStoreOptions().setType("file")
      .setConfig(new JsonObject().put("path", "sd-verticles.json"))))
  private var deploymentId = ""

  def config(): JsonObject = retriever.getConfig.result()

  before {
    deploymentId = Await.result(
      vertx
        .deployVerticleFuture("scala:" + implicitly[TypeTag[A]].tpe.typeSymbol.fullName,
          DeploymentOptions().setConfig(config()))
        .andThen {
          case Success(d) => d
          case Failure(t) => throw new RuntimeException(t)
        },
      10000 millis
    )
  }

  after {
    Await.result(
      vertx.undeployFuture(deploymentId)
        .andThen {
          case Success(d) => d
          case Failure(t) => throw new RuntimeException(t)
        },
      10000 millis
    )
  }

}
