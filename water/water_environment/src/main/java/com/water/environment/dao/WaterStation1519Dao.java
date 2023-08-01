package com.water.environment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WaterStation15_19")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterStation1519Dao {
    //Date	CODcr	ZD	AD	ZL	pH	SS	DDL	RJY	waterTemperature	waterStationName	Location

    @Id
    private String id;

    private String Date;
    private String CODcr;
    private String ZD;
    private String AD;
    private String ZL;
    private String pH;
    private String SS;
    private String DDL;
    private String RJY;
    private String waterTemperature;
    private String waterStationName;
    private String Location;
}
