package com.water.environment.service.Impl;


import com.water.environment.dao.rainfallDO;
import com.water.environment.service.RainfallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RainfallService")
public class RainfallServiceImpl implements RainfallService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ArrayList<String>> findRainFall(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, 14);
        Query query = new Query().with(pageable);
        List<rainfallDO> comment = mongoTemplate.find(query,rainfallDO.class);
        List<ArrayList<String>> pageData = new ArrayList<>();
        long count = mongoTemplate.count(new Query(),rainfallDO.class);
        ArrayList<String> countRow = new ArrayList<>();
        countRow.add(String.valueOf(count));
        pageData.add(countRow);
        for (rainfallDO row :comment){
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(row.getDate());
            rowData.add(row.getCity_County());
            rowData.add(row.getPoint());
            rowData.add(row.getRain());
            pageData.add(rowData);
        }
        return pageData;
    }
}
