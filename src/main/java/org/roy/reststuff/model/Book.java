package org.roy.reststuff.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Book {

  public static String DB = "test";

  public static String COLLECTION = "books";

  @JsonProperty
  private ObjectId id;

  @JsonProperty
  private String title;

  @EqualsAndHashCode.Exclude
  @JsonProperty
  private int available;

}
