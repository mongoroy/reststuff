package org.roy.reststuff.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@AllArgsConstructor
@Getter
@Builder
public class DocumentIdentifierKey {

  private ObjectId key;

}
