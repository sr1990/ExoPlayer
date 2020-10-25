package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SBDClient {
  // static variable single_instance of type Singleton
  private static SBDClient single_instance = null;

  public Map<Integer, SbdOrderlineTable> SBDRepTableOrderline;

  //CONSTRUCTOR
  // private constructor restricted to this class itself
  private SBDClient()
  {
    SBDRepTableOrderline = new HashMap<>();
  }

  //SETTERS
  //Create a sbdOrderline table with representation id.
  public void setSBDTableRep(Integer r , ArrayList<SBDDescriptor> sbdDescriptorList) {
    if (SBDRepTableOrderline.isEmpty() ||
        (!SBDRepTableOrderline.isEmpty() && !SBDRepTableOrderline.containsKey(r))) {
      SbdOrderlineTable sbdOrderlineTable = new SbdOrderlineTable();
      sbdOrderlineTable.populateTable(r, sbdDescriptorList);
      SBDRepTableOrderline.put(r, sbdOrderlineTable);
    }
  }

  //GETTERS
  String getValueFromSBDTable(Integer AdaptationSetId, long SequenceNumber, String key) {
    SbdOrderlineTable tempTable = SBDRepTableOrderline.get(AdaptationSetId);
    String res =  tempTable.getKeysQuery(SequenceNumber, key);
    return res;
  }

  //SbdOrderlineTable getter as per representation id.
  SbdOrderlineTable getSBDTableRep(Integer i) {
      return SBDRepTableOrderline.get(i);
  }

  //SBDClient related
  //check if SBDClient is initialized or not.
  public static boolean isInitilaized() {
    if (single_instance == null) {
      return false;
    }
    return true;
  }

  // static method to create instance of Singleton class
  public static SBDClient getInstance()
  {
    if (single_instance == null)
      single_instance = new SBDClient();

    return single_instance;
  }

}
