package com.yuhtin.commission.pointssystem.api;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Account {

    private final String username;
    private double points;


}
