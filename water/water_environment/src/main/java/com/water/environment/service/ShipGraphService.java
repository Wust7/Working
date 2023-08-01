package com.water.environment.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ShipGraphService {
    List<JSONObject> findshipGraph(Class aClass);
}
