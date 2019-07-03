package org.example.keyvalue.impl

import akka.Done
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, PersistentEntity}
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import play.api.libs.json.{Format, Json}

import scala.collection.immutable.Seq

class KeyValueEntity extends PersistentEntity {

  override type Command = KeyValueCommand[_]
  override type Event = KeyValueEvent
  override type State = KeyValueState

  override def initialState: KeyValueState = KeyValueState("A value")

  override def behavior: Behavior = {
    Actions().onCommand[SetValue, Done] {
      case (SetValue(newValue), ctx, state) =>
        ctx.thenPersist(
          KeyValueChanged(newValue)
        ) { _ =>
          ctx.reply(Done)
        }
    }
  }
}

// Commands
sealed trait KeyValueCommand[R] extends ReplyType[R]

case class SetValue(value: String) extends KeyValueCommand[Done]

object SetValue {
  implicit val format: Format[SetValue] = Json.format
}


// Event
sealed trait KeyValueEvent extends AggregateEvent[KeyValueEvent] {
  def aggregateTag: AggregateEventTag[KeyValueEvent] = KeyValueEvent.Tag
}

object KeyValueEvent {
  val Tag: AggregateEventTag[KeyValueEvent] = AggregateEventTag[KeyValueEvent]
}

case class KeyValueChanged(value: String) extends KeyValueEvent

object KeyValueChanged {
  implicit val format: Format[KeyValueChanged] = Json.format
}


// State
case class KeyValueState(value: String)

object KeyValueState {
  implicit val format: Format[KeyValueState] = Json.format
}


object KeyValueSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[SetValue],
    JsonSerializer[KeyValueChanged],
    JsonSerializer[KeyValueState]
  )
}

