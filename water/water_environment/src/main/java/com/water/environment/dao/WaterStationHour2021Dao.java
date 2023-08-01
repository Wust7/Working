package com.water.environment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WaterStationHour20_21")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WaterStationHour2021Dao {
    //Date	CODcr	flowRate	ZD	AD	YJT	ZL	pH	SS	DDL	RJY	waterTemperature	ZT	ZG	ZM	ZN	waterStationName	Location
    @Id
    private String id;

    private String Date;
    private String CODcr;
    private String flowRate;
    private String ZD;
    private String AD;
    private String YJT;
    private String ZL;
    private String pH;
    private String SS;
    private String DDL;
    private String RJY;
    private String waterTemperature;
    private String ZT;
    private String ZG;
    private String ZM;
    private String ZN;
    private String waterStationName;
    private String Location;
}
