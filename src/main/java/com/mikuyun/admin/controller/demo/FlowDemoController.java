package com.mikuyun.admin.controller.demo;

import com.mikuyun.admin.service.flow.WarmFlowTest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auth mikuyun
 * @since 2026/5/31 16:26
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/flow")
public class FlowDemoController {

    private final WarmFlowTest warmFlowTest;

    @PostMapping(value = "/deploy")
    public void deployFlow() {
        try {
            warmFlowTest.deployFlow();
        } catch (Exception e) {
            log.info("error: {}", e.getMessage(), e);
        }
    }

}
