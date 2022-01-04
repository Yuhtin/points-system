package com.yuhtin.commission.pointssystem.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;

@Data
@Accessors(chain = true)
public class Account {

    private final String username;
    private double points;

    private final HashMap<Hability, Integer> habilities = new HashMap<>();

}
