package com.baixing.user.controller;

import com.baixing.user.entity.User;
import com.baixing.user.service.UserService;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * @author: ao.wang
 *
 * @create: 2020-10-12
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class IndexController {
    
    private final UserService service;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    
    @RequestMapping("/")
    public String index() throws Exception {
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate("index.ftl");
        List<User> users = service.list();
        Map<String, Object> model = Maps.newHashMap();
        model.put("users", users);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return html;
    }
    
}
