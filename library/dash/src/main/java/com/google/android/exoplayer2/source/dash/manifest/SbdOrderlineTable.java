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
      public Map<Long, Map<String, String>> table;

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

    ExecutorService executor = Executors.newFixedThreadPool(5);

    for (SBDDescriptor sbdDescriptor: sbdDescriptorList) {
      Log.d("SBD","SBD file url: "+sbdDescriptor.value);
      Runnable worker = new TableDownloader(sbdDescriptor.value, table);
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

  public static class TableDownloader implements Runnable {

    private final String url;
    private Gson gson;

    public Map<Long, Map<String, String>> table;

    TableDownloader(String url, Map<Long, Map<String, String>> table) {
      this.table = table;
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

