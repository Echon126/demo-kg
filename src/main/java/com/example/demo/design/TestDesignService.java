package com.example.demo.design;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class TestDesignService {

    @Autowired
    private Map<String,StrategyInterface> strategyInterfaceMap;


    public String strategyMethod(String StrategyInterfaceMehod001){
        StrategyInterface strategyInterfaceMehod001 = strategyInterfaceMap.get(StrategyInterfaceMehod001);
        return  strategyInterfaceMehod001.singleStategy("xxxxx");
    }
}
