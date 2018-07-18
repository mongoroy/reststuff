package org.roy.reststuff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Exclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Person {

  public static final String DB = "test";

  public static final String COLLECTION = "persons";

  @JsonProperty
  @JsonSerialize(using = ToStringSerializer.class)
  private ObjectId id;

  @JsonProperty
  private String name;

  @JsonProperty
  @Exclude
  private boolean accountLocked;

}
