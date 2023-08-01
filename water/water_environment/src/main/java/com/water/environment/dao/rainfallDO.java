package com.water.environment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "RAINFALL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class rainfallDO {
    @Id
    private String id;

    private  String Date;
    private String City_County;
    private String point;
    private String rain;

}
