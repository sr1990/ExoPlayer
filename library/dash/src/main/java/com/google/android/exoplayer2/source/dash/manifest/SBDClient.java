package com.google.android.exoplayer2.source.dash.manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SBDClient {
  // static variable single_instance of type Singleton
  private static SBDClient single_instance = null;
 // AdaptationSet : SbdOrderlineTable (SegmentNUmber: map of key value pairs)
  public Map<Integer, SbdOrderlineTable> SBDTableOrderline;

  //CONSTRUCTOR
  // private constructor restricted to this class itself
  private SBDClient()
  {
    SBDTableOrderline = new HashMap<>();
  }

  //SETTERS
  //Create a sbdOrderline table with adaptation set id.
  public void setSBDTable(Integer adaptationSetId , ArrayList<SBDDescriptor> sbdDescriptorList) {
    if (SBDTableOrderline.isEmpty() ||
        (!SBDTableOrderline.isEmpty() && !SBDTableOrderline.containsKey(adaptationSetId))) {
      SbdOrderlineTable sbdOrderlineTable = new SbdOrderlineTable();
      sbdOrderlineTable.populateTable(adaptationSetId, sbdDescriptorList);
      SBDTableOrderline.put(adaptationSetId, sbdOrderlineTable);
    }
  }

  //GETTERS
  String getValueFromSBDTable(Integer AdaptationSetId, long SequenceNumber, String key) {
    SbdOrderlineTable tempTable = SBDTableOrderline.get(AdaptationSetId);
    String res =  tempTable.getKeysQuery(SequenceNumber, key);
    return res;
  }

  //SbdOrderlineTable getter as per representation id.
  SbdOrderlineTable getSBDTableRep(Integer i) {
      return SBDTableOrderline.get(i);
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
