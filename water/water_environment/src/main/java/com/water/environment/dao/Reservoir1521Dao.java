package com.water.environment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Reservoir15_21")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservoir1521Dao {
    @Id
    private String id;

    private String Date;
    private String reservoirName;
    private String waterTemperature;
    private String pHValue;
    private String dissolvedOxygen;
    private String permanganateIndex;
    private String COD;
    private String AD;
    private String ZL;
    private String ZD;
    private String Cu;
    private String Zn;
    private String FX;
    private String Se;
    private String As;
    private String Hg;
    private String Cd;
    private String Cr6;
    private String Pb;
    private String QHW;
    private String HHS;
    private String SYL;
    private String LAS;
    private String SHW;
    private String FC;
    private String Chlorophyll_A;
    private String reservoirMid;
    private String waterIntake;
}
