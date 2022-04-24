package com.example.demo.design;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("StrategyInterfaceMehod002")
public class StrategyInterfaceMehod002 implements StrategyInterface {
    @Override
    public String singleStategy(String name) {
        log.info("StrategyInterfaceMehod002");
        return "StrategyInterfaceMehod002 " + name;
    }
}
