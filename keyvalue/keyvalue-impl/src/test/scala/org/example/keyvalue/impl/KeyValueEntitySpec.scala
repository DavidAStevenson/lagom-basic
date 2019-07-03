package org.example.keyvalue.impl

import akka.actor.ActorSystem
import akka.testkit.TestKit
import com.lightbend.lagom.scaladsl.testkit.PersistentEntityTestDriver
import com.lightbend.lagom.scaladsl.playjson.JsonSerializerRegistry
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class KeyValueEntitySpec extends WordSpec with Matchers with BeforeAndAfterAll {

  private val system = ActorSystem("KeyValueEntitySpec",
    JsonSerializerRegistry.actorSystemSetupFor(KeyValueSerializerRegistry))

  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  private def withTestDriver(block: PersistentEntityTestDriver[KeyValueCommand[_], KeyValueEvent, KeyValueState] => Unit): Unit = {
    val driver = new PersistentEntityTestDriver(system, new KeyValueEntity, "keyvalue-1")
    block(driver)
    driver.getAllIssues should have size 0
  }

  "keyvalue entity" should {

    "allow setting the value for a key" in withTestDriver { driver =>
      val outcome1 = driver.run(SetValue("Pong"))
      outcome1.events should contain only KeyValueChanged("Pong")
      // TODO after getting a GetKeyValue api, add test to get 
    }
  }

}
