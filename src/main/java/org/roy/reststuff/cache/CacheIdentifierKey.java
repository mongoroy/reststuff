package org.roy.reststuff.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CacheIdentifierKey {

  private String key;

}
