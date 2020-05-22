package com.theram.it; /**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */

import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificData;

@org.apache.avro.specific.AvroGenerated
public class smol extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6301050977790160200L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"com.theram.it.smol\",\"fields\":[{\"name\":\"latency\",\"type\":\"double\",\"unit\":\"second\"},{\"name\":\"bandwidth\",\"type\":\"double\",\"unit\":\"gigabyte / second\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<smol> ENCODER =
      new BinaryMessageEncoder<smol>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<smol> DECODER =
      new BinaryMessageDecoder<smol>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<smol> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<smol> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<smol> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<smol>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this com.theram.it.smol to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a com.theram.it.smol from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a com.theram.it.smol instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static smol fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private double latency;
   private double bandwidth;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public smol() {}

  /**
   * All-args constructor.
   * @param latency The new value for latency
   * @param bandwidth The new value for bandwidth
   */
  public smol(Double latency, Double bandwidth) {
    this.latency = latency;
    this.bandwidth = bandwidth;
  }

  public SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return latency;
    case 1: return bandwidth;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: latency = (Double)value$; break;
    case 1: bandwidth = (Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'latency' field.
   * @return The value of the 'latency' field.
   */
  public double getLatency() {
    return latency;
  }


  /**
   * Sets the value of the 'latency' field.
   * @param value the value to set.
   */
  public void setLatency(double value) {
    this.latency = value;
  }

  /**
   * Gets the value of the 'bandwidth' field.
   * @return The value of the 'bandwidth' field.
   */
  public double getBandwidth() {
    return bandwidth;
  }


  /**
   * Sets the value of the 'bandwidth' field.
   * @param value the value to set.
   */
  public void setBandwidth(double value) {
    this.bandwidth = value;
  }

  /**
   * Creates a new com.theram.it.smol RecordBuilder.
   * @return A new com.theram.it.smol RecordBuilder
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Creates a new com.theram.it.smol RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new com.theram.it.smol RecordBuilder
   */
  public static Builder newBuilder(Builder other) {
    if (other == null) {
      return new Builder();
    } else {
      return new Builder(other);
    }
  }

  /**
   * Creates a new com.theram.it.smol RecordBuilder by copying an existing com.theram.it.smol instance.
   * @param other The existing instance to copy.
   * @return A new com.theram.it.smol RecordBuilder
   */
  public static Builder newBuilder(smol other) {
    if (other == null) {
      return new Builder();
    } else {
      return new Builder(other);
    }
  }

  /**
   * RecordBuilder for com.theram.it.smol instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<smol>
    implements org.apache.avro.data.RecordBuilder<smol> {

    private double latency;
    private double bandwidth;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.latency)) {
        this.latency = data().deepCopy(fields()[0].schema(), other.latency);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.bandwidth)) {
        this.bandwidth = data().deepCopy(fields()[1].schema(), other.bandwidth);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing com.theram.it.smol instance
     * @param other The existing instance to copy.
     */
    private Builder(smol other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.latency)) {
        this.latency = data().deepCopy(fields()[0].schema(), other.latency);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.bandwidth)) {
        this.bandwidth = data().deepCopy(fields()[1].schema(), other.bandwidth);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'latency' field.
      * @return The value.
      */
    public double getLatency() {
      return latency;
    }


    /**
      * Sets the value of the 'latency' field.
      * @param value The value of 'latency'.
      * @return This builder.
      */
    public Builder setLatency(double value) {
      validate(fields()[0], value);
      this.latency = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'latency' field has been set.
      * @return True if the 'latency' field has been set, false otherwise.
      */
    public boolean hasLatency() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'latency' field.
      * @return This builder.
      */
    public Builder clearLatency() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'bandwidth' field.
      * @return The value.
      */
    public double getBandwidth() {
      return bandwidth;
    }


    /**
      * Sets the value of the 'bandwidth' field.
      * @param value The value of 'bandwidth'.
      * @return This builder.
      */
    public Builder setBandwidth(double value) {
      validate(fields()[1], value);
      this.bandwidth = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'bandwidth' field has been set.
      * @return True if the 'bandwidth' field has been set, false otherwise.
      */
    public boolean hasBandwidth() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'bandwidth' field.
      * @return This builder.
      */
    public Builder clearBandwidth() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public smol build() {
      try {
        smol record = new smol();
        record.latency = fieldSetFlags()[0] ? this.latency : (Double) defaultValue(fields()[0]);
        record.bandwidth = fieldSetFlags()[1] ? this.bandwidth : (Double) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<smol>
    WRITER$ = (org.apache.avro.io.DatumWriter<smol>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<smol>
    READER$ = (org.apache.avro.io.DatumReader<smol>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeDouble(this.latency);

    out.writeDouble(this.bandwidth);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.latency = in.readDouble();

      this.bandwidth = in.readDouble();

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.latency = in.readDouble();
          break;

        case 1:
          this.bandwidth = in.readDouble();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










