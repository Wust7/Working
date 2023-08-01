package com.water.environment.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipGraphDao implements Serializable {
    @Id
    private String id;

    private String type;
    private Object properties;
    private Object geometry;
}
