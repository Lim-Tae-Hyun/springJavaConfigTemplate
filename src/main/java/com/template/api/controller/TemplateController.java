package com.template.api.controller;

import com.template.api.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @discription : template controller class
 * @author : limth
 */
/* @RestController : include @Controller and @ResponseBody annotation attribute */
@RestController
public class TemplateController {

    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService templateService;

    @GetMapping(value = "/testGet")
    public ModelAndView testGet() throws Exception {

        ModelAndView mav = new ModelAndView();

        log.debug("called get controller");

        mav.addObject("testStr", "success");
        mav.setViewName("template");

        return mav;
    }

    @PostMapping(value = "/testPost")
    public void testPost() throws Exception {

        log.debug("called post controller");
    }

    @DeleteMapping(value = "/testDelete")
    public void testDelete() throws Exception {

        log.debug("called delete controller");
    }

    @PutMapping(value = "/testPut")
    public void testPut() throws Exception {

        log.debug("called put controller");
    }

}
