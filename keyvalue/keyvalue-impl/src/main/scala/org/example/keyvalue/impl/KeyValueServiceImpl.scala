package org.example.keyvalue.impl

import org.example.keyvalue.api
import org.example.keyvalue.api.KeyValueService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry


class KeyValueServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends KeyValueService {

  override def setKeyValue(key: String) = ServiceCall { request =>
    val ref = persistentEntityRegistry.refFor[KeyValueEntity](key)

    ref.ask(SetValue(request.value))
  }
}

