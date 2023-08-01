package com.water.environment.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BWSN2008Service {
    JSONArray pipeTest();

    List<JSONObject> select();
}
