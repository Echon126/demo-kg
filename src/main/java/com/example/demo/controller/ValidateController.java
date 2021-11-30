package com.example.demo.controller;

import com.example.demo.common.APIResultBody;
import com.example.demo.domain.entity.Application;
import com.example.demo.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/validate")
@Api(tags = "数据校验 Demo")
public class ValidateController {

    @PostMapping("/v")
    @ApiOperation(value = "新增修改货物信息")
    public APIResultBody validateInfo(@Valid @RequestBody Application application) {
        return APIResultBody.success();
    }
}
