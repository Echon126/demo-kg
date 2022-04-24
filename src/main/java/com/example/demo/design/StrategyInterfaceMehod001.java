package com.example.demo.design;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("StrategyInterfaceMehod001")
public class StrategyInterfaceMehod001 implements StrategyInterface{
    @Override
    public String singleStategy(String name) {
        log.info("StrategyInterfaceMehod001");
        return "StrategyInterfaceMehod001 " + name;
    }
}
