package com.example.demo.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Configuration
public class ValidatorConfiguration {

    //TODO 注解方式校验提交的参数 https://www.cnblogs.com/mr-yang-localhost/p/7812038.html
    //TODO https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-provider-specific-settings
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //设置为快速模式，只要有一个验证失败就返回
                .failFast(true)
                //.addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }
}
