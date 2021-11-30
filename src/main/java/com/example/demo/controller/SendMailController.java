package com.example.demo.controller;

import com.example.demo.utils.MailContentModel;
import com.example.demo.utils.SendMailServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "发送邮件测试")
@RestController
@RequestMapping("/mail")
public class SendMailController {
    @Autowired
    private SendMailServiceImpl sendMailService;

    @GetMapping("/m")
    public String sendMail() {
        this.sendMailService.sendTxtMail("15620742723@163.com", "业务方案", "我的测试有奖");
        this.sendMailService.sendHtmlMail("15620742723@163.com", "业务方案", MailContentModel.builder()
                .messageCode("code")
                .cause("xxxxxx")
                .messageStatus("xxxxx").build());
        return "SUCCESS";
    }
}
