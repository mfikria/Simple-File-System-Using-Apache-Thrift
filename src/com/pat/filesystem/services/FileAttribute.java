package com.pat.filesystem.services;

/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */


import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-10-13")
public class FileAttribute implements org.apache.thrift.TBase<FileAttribute, FileAttribute._Fields>, java.io.Serializable, Cloneable, Comparable<FileAttribute> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FileAttribute");

  private static final org.apache.thrift.protocol.TField FILE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("fileName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DIRECTORY_FIELD_DESC = new org.apache.thrift.protocol.TField("directory", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("size", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField LAST_MODIFIED_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("lastModifiedDate", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField CREATED_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("createdDate", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FileAttributeStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FileAttributeTupleSchemeFactory());
  }

  public String fileName; // required
  public boolean directory; // required
  public long size; // required
  public String lastModifiedDate; // required
  public String createdDate; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_NAME((short)1, "fileName"),
    DIRECTORY((short)2, "directory"),
    SIZE((short)3, "size"),
    LAST_MODIFIED_DATE((short)4, "lastModifiedDate"),
    CREATED_DATE((short)5, "createdDate");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // FILE_NAME
          return FILE_NAME;
        case 2: // DIRECTORY
          return DIRECTORY;
        case 3: // SIZE
          return SIZE;
        case 4: // LAST_MODIFIED_DATE
          return LAST_MODIFIED_DATE;
        case 5: // CREATED_DATE
          return CREATED_DATE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __DIRECTORY_ISSET_ID = 0;
  private static final int __SIZE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILE_NAME, new org.apache.thrift.meta_data.FieldMetaData("fileName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DIRECTORY, new org.apache.thrift.meta_data.FieldMetaData("directory", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.SIZE, new org.apache.thrift.meta_data.FieldMetaData("size", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "int64")));
    tmpMap.put(_Fields.LAST_MODIFIED_DATE, new org.apache.thrift.meta_data.FieldMetaData("lastModifiedDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "DateTime")));
    tmpMap.put(_Fields.CREATED_DATE, new org.apache.thrift.meta_data.FieldMetaData("createdDate", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , "DateTime")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FileAttribute.class, metaDataMap);
  }

  public FileAttribute() {
  }

  public FileAttribute(
    String fileName,
    boolean directory,
    long size,
    String lastModifiedDate,
    String createdDate)
  {
    this();
    this.fileName = fileName;
    this.directory = directory;
    setDirectoryIsSet(true);
    this.size = size;
    setSizeIsSet(true);
    this.lastModifiedDate = lastModifiedDate;
    this.createdDate = createdDate;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FileAttribute(FileAttribute other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFileName()) {
      this.fileName = other.fileName;
    }
    this.directory = other.directory;
    this.size = other.size;
    if (other.isSetLastModifiedDate()) {
      this.lastModifiedDate = other.lastModifiedDate;
    }
    if (other.isSetCreatedDate()) {
      this.createdDate = other.createdDate;
    }
  }

  public FileAttribute deepCopy() {
    return new FileAttribute(this);
  }

  @Override
  public void clear() {
    this.fileName = null;
    setDirectoryIsSet(false);
    this.directory = false;
    setSizeIsSet(false);
    this.size = 0;
    this.lastModifiedDate = null;
    this.createdDate = null;
  }

  public String getFileName() {
    return this.fileName;
  }

  public FileAttribute setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public void unsetFileName() {
    this.fileName = null;
  }

  /** Returns true if field fileName is set (has been assigned a value) and false otherwise */
  public boolean isSetFileName() {
    return this.fileName != null;
  }

  public void setFileNameIsSet(boolean value) {
    if (!value) {
      this.fileName = null;
    }
  }

  public boolean isDirectory() {
    return this.directory;
  }

  public FileAttribute setDirectory(boolean directory) {
    this.directory = directory;
    setDirectoryIsSet(true);
    return this;
  }

  public void unsetDirectory() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DIRECTORY_ISSET_ID);
  }

  /** Returns true if field directory is set (has been assigned a value) and false otherwise */
  public boolean isSetDirectory() {
    return EncodingUtils.testBit(__isset_bitfield, __DIRECTORY_ISSET_ID);
  }

  public void setDirectoryIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DIRECTORY_ISSET_ID, value);
  }

  public long getSize() {
    return this.size;
  }

  public FileAttribute setSize(long size) {
    this.size = size;
    setSizeIsSet(true);
    return this;
  }

  public void unsetSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SIZE_ISSET_ID);
  }

  /** Returns true if field size is set (has been assigned a value) and false otherwise */
  public boolean isSetSize() {
    return EncodingUtils.testBit(__isset_bitfield, __SIZE_ISSET_ID);
  }

  public void setSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SIZE_ISSET_ID, value);
  }

  public String getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  public FileAttribute setLastModifiedDate(String lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
    return this;
  }

  public void unsetLastModifiedDate() {
    this.lastModifiedDate = null;
  }

  /** Returns true if field lastModifiedDate is set (has been assigned a value) and false otherwise */
  public boolean isSetLastModifiedDate() {
    return this.lastModifiedDate != null;
  }

  public void setLastModifiedDateIsSet(boolean value) {
    if (!value) {
      this.lastModifiedDate = null;
    }
  }

  public String getCreatedDate() {
    return this.createdDate;
  }

  public FileAttribute setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  public void unsetCreatedDate() {
    this.createdDate = null;
  }

  /** Returns true if field createdDate is set (has been assigned a value) and false otherwise */
  public boolean isSetCreatedDate() {
    return this.createdDate != null;
  }

  public void setCreatedDateIsSet(boolean value) {
    if (!value) {
      this.createdDate = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FILE_NAME:
      if (value == null) {
        unsetFileName();
      } else {
        setFileName((String)value);
      }
      break;

    case DIRECTORY:
      if (value == null) {
        unsetDirectory();
      } else {
        setDirectory((Boolean)value);
      }
      break;

    case SIZE:
      if (value == null) {
        unsetSize();
      } else {
        setSize((Long)value);
      }
      break;

    case LAST_MODIFIED_DATE:
      if (value == null) {
        unsetLastModifiedDate();
      } else {
        setLastModifiedDate((String)value);
      }
      break;

    case CREATED_DATE:
      if (value == null) {
        unsetCreatedDate();
      } else {
        setCreatedDate((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_NAME:
      return getFileName();

    case DIRECTORY:
      return isDirectory();

    case SIZE:
      return getSize();

    case LAST_MODIFIED_DATE:
      return getLastModifiedDate();

    case CREATED_DATE:
      return getCreatedDate();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FILE_NAME:
      return isSetFileName();
    case DIRECTORY:
      return isSetDirectory();
    case SIZE:
      return isSetSize();
    case LAST_MODIFIED_DATE:
      return isSetLastModifiedDate();
    case CREATED_DATE:
      return isSetCreatedDate();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FileAttribute)
      return this.equals((FileAttribute)that);
    return false;
  }

  public boolean equals(FileAttribute that) {
    if (that == null)
      return false;

    boolean this_present_fileName = true && this.isSetFileName();
    boolean that_present_fileName = true && that.isSetFileName();
    if (this_present_fileName || that_present_fileName) {
      if (!(this_present_fileName && that_present_fileName))
        return false;
      if (!this.fileName.equals(that.fileName))
        return false;
    }

    boolean this_present_directory = true;
    boolean that_present_directory = true;
    if (this_present_directory || that_present_directory) {
      if (!(this_present_directory && that_present_directory))
        return false;
      if (this.directory != that.directory)
        return false;
    }

    boolean this_present_size = true;
    boolean that_present_size = true;
    if (this_present_size || that_present_size) {
      if (!(this_present_size && that_present_size))
        return false;
      if (this.size != that.size)
        return false;
    }

    boolean this_present_lastModifiedDate = true && this.isSetLastModifiedDate();
    boolean that_present_lastModifiedDate = true && that.isSetLastModifiedDate();
    if (this_present_lastModifiedDate || that_present_lastModifiedDate) {
      if (!(this_present_lastModifiedDate && that_present_lastModifiedDate))
        return false;
      if (!this.lastModifiedDate.equals(that.lastModifiedDate))
        return false;
    }

    boolean this_present_createdDate = true && this.isSetCreatedDate();
    boolean that_present_createdDate = true && that.isSetCreatedDate();
    if (this_present_createdDate || that_present_createdDate) {
      if (!(this_present_createdDate && that_present_createdDate))
        return false;
      if (!this.createdDate.equals(that.createdDate))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_fileName = true && (isSetFileName());
    list.add(present_fileName);
    if (present_fileName)
      list.add(fileName);

    boolean present_directory = true;
    list.add(present_directory);
    if (present_directory)
      list.add(directory);

    boolean present_size = true;
    list.add(present_size);
    if (present_size)
      list.add(size);

    boolean present_lastModifiedDate = true && (isSetLastModifiedDate());
    list.add(present_lastModifiedDate);
    if (present_lastModifiedDate)
      list.add(lastModifiedDate);

    boolean present_createdDate = true && (isSetCreatedDate());
    list.add(present_createdDate);
    if (present_createdDate)
      list.add(createdDate);

    return list.hashCode();
  }

  @Override
  public int compareTo(FileAttribute other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFileName()).compareTo(other.isSetFileName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileName, other.fileName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDirectory()).compareTo(other.isSetDirectory());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDirectory()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.directory, other.directory);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSize()).compareTo(other.isSetSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.size, other.size);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastModifiedDate()).compareTo(other.isSetLastModifiedDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastModifiedDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastModifiedDate, other.lastModifiedDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreatedDate()).compareTo(other.isSetCreatedDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreatedDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createdDate, other.createdDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FileAttribute(");
    boolean first = true;

    sb.append("fileName:");
    if (this.fileName == null) {
      sb.append("null");
    } else {
      sb.append(this.fileName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("directory:");
    sb.append(this.directory);
    first = false;
    if (!first) sb.append(", ");
    sb.append("size:");
    sb.append(this.size);
    first = false;
    if (!first) sb.append(", ");
    sb.append("lastModifiedDate:");
    if (this.lastModifiedDate == null) {
      sb.append("null");
    } else {
      sb.append(this.lastModifiedDate);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createdDate:");
    if (this.createdDate == null) {
      sb.append("null");
    } else {
      sb.append(this.createdDate);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FileAttributeStandardSchemeFactory implements SchemeFactory {
    public FileAttributeStandardScheme getScheme() {
      return new FileAttributeStandardScheme();
    }
  }

  private static class FileAttributeStandardScheme extends StandardScheme<FileAttribute> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FileAttribute struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileName = iprot.readString();
              struct.setFileNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DIRECTORY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.directory = iprot.readBool();
              struct.setDirectoryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.size = iprot.readI64();
              struct.setSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LAST_MODIFIED_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.lastModifiedDate = iprot.readString();
              struct.setLastModifiedDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATED_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.createdDate = iprot.readString();
              struct.setCreatedDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FileAttribute struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fileName != null) {
        oprot.writeFieldBegin(FILE_NAME_FIELD_DESC);
        oprot.writeString(struct.fileName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(DIRECTORY_FIELD_DESC);
      oprot.writeBool(struct.directory);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SIZE_FIELD_DESC);
      oprot.writeI64(struct.size);
      oprot.writeFieldEnd();
      if (struct.lastModifiedDate != null) {
        oprot.writeFieldBegin(LAST_MODIFIED_DATE_FIELD_DESC);
        oprot.writeString(struct.lastModifiedDate);
        oprot.writeFieldEnd();
      }
      if (struct.createdDate != null) {
        oprot.writeFieldBegin(CREATED_DATE_FIELD_DESC);
        oprot.writeString(struct.createdDate);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FileAttributeTupleSchemeFactory implements SchemeFactory {
    public FileAttributeTupleScheme getScheme() {
      return new FileAttributeTupleScheme();
    }
  }

  private static class FileAttributeTupleScheme extends TupleScheme<FileAttribute> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FileAttribute struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetFileName()) {
        optionals.set(0);
      }
      if (struct.isSetDirectory()) {
        optionals.set(1);
      }
      if (struct.isSetSize()) {
        optionals.set(2);
      }
      if (struct.isSetLastModifiedDate()) {
        optionals.set(3);
      }
      if (struct.isSetCreatedDate()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetFileName()) {
        oprot.writeString(struct.fileName);
      }
      if (struct.isSetDirectory()) {
        oprot.writeBool(struct.directory);
      }
      if (struct.isSetSize()) {
        oprot.writeI64(struct.size);
      }
      if (struct.isSetLastModifiedDate()) {
        oprot.writeString(struct.lastModifiedDate);
      }
      if (struct.isSetCreatedDate()) {
        oprot.writeString(struct.createdDate);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FileAttribute struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.fileName = iprot.readString();
        struct.setFileNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.directory = iprot.readBool();
        struct.setDirectoryIsSet(true);
      }
      if (incoming.get(2)) {
        struct.size = iprot.readI64();
        struct.setSizeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lastModifiedDate = iprot.readString();
        struct.setLastModifiedDateIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createdDate = iprot.readString();
        struct.setCreatedDateIsSet(true);
      }
    }
  }

}

