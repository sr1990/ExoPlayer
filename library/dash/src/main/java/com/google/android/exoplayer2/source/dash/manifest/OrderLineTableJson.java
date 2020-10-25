package com.google.android.exoplayer2.source.dash.manifest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderLineTableJson {

  @SerializedName("KeyValue")
  @Expose
  private List<KeyValue> keyValue = null;

  /**
   * No args constructor for use in serialization
   *
   */
  public OrderLineTableJson() {
  }

  /**
   *
   * @param keyValue
   */
  public OrderLineTableJson(List<KeyValue> keyValue) {
    super();
    this.keyValue = keyValue;
  }

  public List<KeyValue> getKeyValue() {
    return keyValue;
  }

  public void setKeyValue(List<KeyValue> keyValue) {
    this.keyValue = keyValue;
  }

}