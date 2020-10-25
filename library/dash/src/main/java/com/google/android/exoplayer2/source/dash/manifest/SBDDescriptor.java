package com.google.android.exoplayer2.source.dash.manifest;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import java.util.Map;

public class SBDDescriptor {

  /** The scheme URI. */
  public final String schemeIdUri;
  /**
   * The value, or null.
   */
  @Nullable
  public final String value;
  /**
   * The identifier, or null.
   */
  @Nullable public final String id;

  @Nullable public final String template;

  public final Map<String, String> keyValuePairs;

  /**
   * @param schemeIdUri The scheme URI.
   * @param value The value, or null.
   * @param id The identifier, or null.
   */
  public SBDDescriptor(String schemeIdUri, @Nullable String value, @Nullable String id, @Nullable String template,
                        Map<String,String> keyValuePairs) {
    this.schemeIdUri = schemeIdUri;
    this.value = value;
    this.id = id;
    this.template = template;
    this.keyValuePairs = keyValuePairs;
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SBDDescriptor other = (SBDDescriptor) obj;
    return Util.areEqual(schemeIdUri, other.schemeIdUri) && Util.areEqual(value, other.value)
        && Util.areEqual(id, other.id) && Util.areEqual(template, other.template) &&
          Util.areEqual(keyValuePairs, other.keyValuePairs);
  }

  @Override
  public int hashCode() {
    int result = schemeIdUri.hashCode();
    result = 31 * result + (value != null ? value.hashCode() : 0);
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (template != null ? template.hashCode() : 0);
    result = 31 * result + (keyValuePairs != null ? keyValuePairs.hashCode() : 0);
    return result;
  }
}

