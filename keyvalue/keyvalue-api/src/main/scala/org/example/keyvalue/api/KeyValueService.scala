package org.example.keyvalue.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

trait KeyValueService extends Service {
  def setKeyValue(value: String): ServiceCall[KeyValueMessage, Done]

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("keyvalue")
      .withCalls(
        pathCall("/api/key/:key", setKeyValue _)
      )
      .withAutoAcl(true)
    // @formatter:on
  }
}

case class KeyValueMessage(value: String)

object KeyValueMessage {
  implicit val format: Format[KeyValueMessage] = Json.format[KeyValueMessage]
}
