package com.theram.it.model

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.JsonObject
import org.apache.avro.generic.GenericData

import scala.jdk.CollectionConverters.ListHasAsScala

class ReferenceDataCodec
    extends MessageCodec[ReferenceDataHolder, ReferenceDataHolder] {
  override def encodeToWire(buffer: Buffer,
                            record: ReferenceDataHolder): Unit = {
    val jsonToEncode = new JsonObject()
    jsonToEncode.put("Keys", record.key)
    jsonToEncode.put("Values", record.value)
    jsonToEncode.put("topic", record.collectionName)
    val jsonToStr = jsonToEncode.encode
    val length = jsonToStr.getBytes.length
    // Write data into given buffer
    buffer.appendInt(length)
    buffer.appendString(jsonToStr)
  }

  override def decodeFromWire(pos: Int, buffer: Buffer): ReferenceDataHolder = {
    val length = buffer.getInt(pos)
    // Get JSON string by it`s length
    // Jump 4 because getInt() == 4 bytes
    val jsonStr = buffer.getString(pos + 4, pos + 4 + length)
    val contentJson = new JsonObject(jsonStr)

    // Get fields
    val keyFields = contentJson.getString("Key")
    val valueFields = contentJson.getString("Value")
    val topic = contentJson.getString("topic")

    // We can finally create custom message object
    ReferenceDataHolder(collectionName = topic,
                        key = keyFields,
                        value = valueFields)
  }

  override def transform(s: ReferenceDataHolder): ReferenceDataHolder = s

  override def name(): String = getClass.getName

  override def systemCodecID(): Byte = -1

  private def valuesToMap(genericDataRecord: GenericData.Record) = {
    genericDataRecord.getSchema.getFields.asScala
      .map(schemaField => schemaField.name())
      .map(fieldName => fieldName -> genericDataRecord.get(fieldName))
      .filter(x => x._2 != null)
      .map(y => y._1 -> y._2.toString)
      .toMap
  }
}
