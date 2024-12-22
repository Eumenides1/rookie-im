package com.rookie.stack.im.controller.user;

import com.rookie.stack.im.common.annotations.SkipAppIdValidation;
import com.rookie.stack.push.MessagePushService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Name：TestController
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
@RestController("/test")
public class TestController {

    @Resource
    MessagePushService messagePushService;

    @SkipAppIdValidation
    @GetMapping("/send-text-email")
    public String sendTextEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content) {
        messagePushService.sendText(to, subject, content);
        return "Text email sent successfully!";
    }
    @SkipAppIdValidation
    @GetMapping("/send-template-email")
    public String sendTemplateEmail(@RequestParam String to, @RequestParam String subject) {
        Map<String, Object> model = Map.of("user", "John", "content", "Welcome to our service!");
        messagePushService.sendTemplate(to, subject, "welcome.ftl", model);
        return "Template email sent successfully!";
    }
    @SkipAppIdValidation
    @GetMapping("/send-custom-template-email")
    public String sendCustomTemplateEmail() {
        Map<String, Object> model = new HashMap<>();
        model.put("user", "John Doe");
        model.put("code", "123456");
        messagePushService.sendTemplate("1763077056@qq.com", "Your Verification Code", "verification-code.ftl", model);
        return "Custom template email sent successfully!";
    }

}
