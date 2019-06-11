package com.example.openapi.service.impl;

import com.example.openapi.service.TestService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    @Async
    public void doAsync() {
        try {
            Thread.sleep(100);
            System.out.println("异步1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 1 / 0;
    }
}
