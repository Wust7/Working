package com.water.environment.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.water.environment.dao.ShipGraphDao;
import com.water.environment.service.ShipGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ShipGraphService")
public class ShipGraphServiceImpl implements ShipGraphService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<JSONObject> findshipGraph(Class aClass) {
        List<ShipGraphDao> shipGraphList = mongoTemplate.findAll(aClass);
        List<JSONObject> OUTFALLMap = new ArrayList<>();
        JSONObject typeObject=new JSONObject();
        typeObject.put("type","GeometryCollection");
        OUTFALLMap.add(typeObject);
        List<Object> allPoint = new ArrayList<>();
        for (ShipGraphDao comment : shipGraphList) {
            allPoint.add(comment.getGeometry());
        }
        JSONObject allPointJSON=new JSONObject();
        allPointJSON.put("geometries",allPoint);
        OUTFALLMap.add(allPointJSON);
        return OUTFALLMap;
    }
}
