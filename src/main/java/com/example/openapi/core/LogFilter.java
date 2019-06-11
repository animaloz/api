package com.example.openapi.core;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/5/7
 */
@Activate(group = {Constants.CONSUMER}, value = "consumerLogFilter")
public class LogFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        log(invoker, invocation, result);
        return result;
    }

    private void log(Invoker<?> invoker, Invocation invocation, Result result) {
        String name = invoker.getInterface().getName();
        String methodName = invocation.getMethodName();
        Object[] arguments = invocation.getArguments();
        try {
            String resultJson = JSON.toJSONString(result.getValue());
            if (resultJson != null && resultJson.length() < 1000L) {
                logger.info("{}.{}({}) --->>> {}", name, methodName, arguments, resultJson);
            } else {
                logger.info("{}.{}({}) --->>> {}", name, methodName, arguments, "返回结果转换为String长度超过1000");
            }
        } catch (Exception e) {
            logger.error("{}.{}({}) 打印日志出错", name, methodName, arguments);
        }
    }
}
