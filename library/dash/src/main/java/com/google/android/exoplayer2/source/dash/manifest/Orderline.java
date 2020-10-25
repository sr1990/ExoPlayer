
package com.google.android.exoplayer2.source.dash.manifest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderline {

  @SerializedName("v")
  @Expose
  private List<String> v = null;

  /**
   * No args constructor for use in serialization
   */
  public Orderline() {
  }

  /**
   * @param v
   */
  public Orderline(List<String> v) {
    super();
    this.v = v;
  }

  public List<String> getV() {
    return v;
  }

  public void setV(List<String> v) {
    this.v = v;
  }
}