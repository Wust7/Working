package com.water.resource.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "comment2")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDO implements Serializable {

    @Id
    private String id;

    private String driverName;
    private String monitorPoint;
    private String monitorTime;
    private String waterTemperature;
    private String pHValue;
    private String dissolvedOxygen;
    private String permanganateIndex;
    private String COD;// 化学需氧量
    private String BOD;// 生化需氧量
    private String feature;// 标识所有非常用元素
    private String gnotes;


}
