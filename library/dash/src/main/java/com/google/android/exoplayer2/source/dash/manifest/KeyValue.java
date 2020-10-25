package com.google.android.exoplayer2.source.dash.manifest;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyValue {

  @SerializedName("keylist")
  @Expose
  private List<String> keylist = null;
  @SerializedName("comment")
  @Expose
  private String comment;
  @SerializedName("orderline")
  @Expose
  private List<Orderline> orderline = null;

  /**
   * No args constructor for use in serialization
   *
   */
  public KeyValue() {
  }

  /**
   *
   * @param orderline
   * @param comment
   * @param keylist
   */
  public KeyValue(List<String> keylist, String comment, List<Orderline> orderline) {
    super();
    this.keylist = keylist;
    this.comment = comment;
    this.orderline = orderline;
  }

  public List<String> getKeylist() {
    return keylist;
  }

  public void setKeylist(List<String> keylist) {
    this.keylist = keylist;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<Orderline> getOrderline() {
    return orderline;
  }

  public void setOrderline(List<Orderline> orderline) {
    this.orderline = orderline;
  }

}