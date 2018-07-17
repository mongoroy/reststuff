package org.roy.reststuff.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;

@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DocumentIdentifierKey {

  private ObjectId key;

}
