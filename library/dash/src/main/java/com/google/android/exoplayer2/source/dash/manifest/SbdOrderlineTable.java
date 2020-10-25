package com.google.android.exoplayer2.source.dash.manifest;

import com.google.android.exoplayer2.util.Log;
import com.google.common.base.Charsets;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SbdOrderlineTable {
      public static Map<Long, Map<String, String>> table;

  public SbdOrderlineTable() {
    table = Collections.synchronizedMap(new HashMap<>());
  }

  //Get table
  public Map<Long, Map<String, String>> getTable() {
    return table;
  }

  //Method to return all maps.
  public Map<String, String> getKeys(Long sequenceNumber) {
    return table.get(sequenceNumber);
  }

  //Method to get values of multiple keys. Get only a few keys from the table.
  public String getKeysQuery(Long sequenceNumber, String key) {
    Map<String,String> res = new HashMap<String,String>();
    Map<String,String> tempRes = table.get(sequenceNumber);
      if (tempRes.containsKey(key)){
         return tempRes.get(key);
      }
      return "";
  }

  //getQuery: Go through all the key-values and create a string query.
  //Template not taken into consideration yet.

  public String getStringQuery(Long sequenceNumber) {
    String res = new String();
    Map<String,String> tempRes = table.get(sequenceNumber);
    for (Map.Entry<String,String> s: tempRes.entrySet()) {
      res += "?" + s.getKey() + "=" + s.getValue();
    }
    Log.d("SBD","Returning query: "+res);
    return res;
  }

  Map<String,String> setKey(String key, String value) {
    Map<String, String> e1 = new HashMap<String,String>();
    e1.put(key,value);
    return e1;
  }

  public void populateTable(Integer r , ArrayList<SBDDescriptor> sbdDescriptorList) {
   /* Map<String, String> keyValue1 = new HashMap<>();
    keyValue1.put("k1","r1");
    keyValue1.put("k2","r1");
    keyValue1.put("k3","r1");
    keyValue1.put("k4","r1");
    table.put(new Long(1), keyValue1);

    Map<String, String> keyValue2 = new HashMap<>();
    keyValue2.put("k1","r2");
    keyValue2.put("k2","r2");
    keyValue2.put("k3","r2");
    keyValue2.put("k4","r2");
    table.put(new Long(2), keyValue2);

    Map<String, String> keyValue3 = new HashMap<>();
    keyValue3.put("k1","r3");
    keyValue3.put("k2","r3");
    keyValue3.put("k3","r3");
    keyValue3.put("k4","r3");
    table.put(new Long(3), keyValue3);

    Map<String, String> keyValue4 = new HashMap<>();
    keyValue4.put("k1","r4");
    keyValue4.put("k2","r4");
    keyValue4.put("k3","r4");
    keyValue4.put("k4","r4");
    table.put(new Long(4), keyValue4);

    Map<String, String> keyValue5 = new HashMap<>();
    keyValue5.put("k1","r5");
    keyValue5.put("k2","r5");
    keyValue5.put("k3","r5");
    keyValue5.put("k4","r5");
    table.put(new Long(5), keyValue5);

    Map<String, String> keyValue6 = new HashMap<>();
    keyValue6.put("k1","r6");
    keyValue6.put("k2","r6");
    keyValue6.put("k3","r6");
    keyValue6.put("k4","r6");
    table.put(new Long(6), keyValue6);

    Map<String, String> keyValue7 = new HashMap<>();
    keyValue7.put("k1","r7");
    keyValue7.put("k2","r7");
    keyValue7.put("k3","r7");
    keyValue7.put("k4","r7");
    table.put(new Long(7), keyValue7);

    Map<String, String> keyValue8 = new HashMap<>();
    keyValue8.put("k1","r8");
    keyValue8.put("k2","r8");
    keyValue8.put("k3","r8");
    keyValue8.put("k4","r8");
    table.put(new Long(8), keyValue8);

    Map<String, String> keyValue9 = new HashMap<>();
    keyValue9.put("k1","r9");
    keyValue9.put("k2","r9");
    keyValue9.put("k3","r9");
    keyValue9.put("k4","r9");
    table.put(new Long(9), keyValue9);

    Map<String, String> keyValue10 = new HashMap<>();
    keyValue10.put("k1","r10");
    keyValue10.put("k2","r10");
    keyValue10.put("k3","r10");
    keyValue10.put("k4","r10");
    table.put(new Long(10), keyValue10);

    Map<String, String> keyValue11 = new HashMap<>();
    keyValue11.put("k1","r11");
    keyValue11.put("k2","r11");
    keyValue11.put("k3","r11");
    keyValue11.put("k4","r11");
    table.put(new Long(11), keyValue11);
*/

    ExecutorService executor = Executors.newFixedThreadPool(5);

    for (SBDDescriptor sbdDescriptor: sbdDescriptorList) {
      Log.d("SBD","SBD file url: "+sbdDescriptor.value);
      Runnable worker = new MyRunnable(sbdDescriptor.value);
      executor.execute(worker);
    }
    executor.shutdown();
    // Wait until all threads are finish
    while (!executor.isTerminated()) {}
    Log.d("SBD","All threads done and table populated.");

  }

  /*
  {
    "KeyValue": [
    {
      "keylist": ["value"],
      "comment": "A/B sequence document for user Ctulhu",
        "orderline": [
      {"v": ["861d34d7-56eb-4893-a7b7-60edabebe3e6"]},
      {"v": ["d8a56fd3-6c21-44be-94f8-a519cd6b4169"]},
      {"v": ["75b49311-008c-4272-9aff-b855ee94707a"]}
            ]
    }
    ]
  }
 */

  public static class MyRunnable implements Runnable {

    private final String url;
    private Gson gson;

    MyRunnable(String url) {
      this.url = url;
      gson = new Gson();
    }

    @Override
    public void run() {
      try {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
            new URL(url).openStream());
        String strFileContents = new String(ByteStreams.toByteArray(bufferedInputStream),
            Charsets.ISO_8859_1);

        OrderLineTableJson orderLineTableJson = gson.fromJson(strFileContents, OrderLineTableJson.class);
        String keysString = orderLineTableJson.getKeyValue().get(0).getKeylist().toString();
        keysString = keysString.substring(1, keysString.length() - 1);
        String[] keys = keysString.split(" ");

        long segmentCount = 1;
        for (Orderline valueList : orderLineTableJson.getKeyValue().get(0).getOrderline()) {
          String ValuesString = valueList.getV().toString().substring(1, valueList.getV().toString().length() - 1);
          String[] values = ValuesString.split(" ");

          Map<String, String> keyValuePair = Collections.synchronizedMap(new HashMap<>());
          //Log.d("SBD", "Segment Number: "+segmentCount);

          for (int i=0;i<values.length;i++) {
//            Log.d("SBD", "Key: "+ keys[i] +"  Value: "+values[i]);
            keyValuePair.put(keys[i],values[i]);
          }

          if (!table.containsKey(Long.valueOf(segmentCount))) {
            table.put(new Long(segmentCount), keyValuePair);
          } else {
            for (Map.Entry<String,String> keyValue: keyValuePair.entrySet())
              table.get(segmentCount).put(keyValue.getKey(), keyValue.getValue());
          }

          segmentCount++;
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }
}

