package com.thread;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by
 * on 15/8/28.
 * Description:
 */
public class ThreadLocalMy {
    private static ThreadLocal<String> traceIdHoler = ThreadLocal.withInitial(new Supplier<String>() {
        @Override
        public String get() {
            return "init";
        }
    });

    public static String getTraceId() {
        String traceId = "";
        if (StringUtils.isBlank(traceIdHoler.get())) {
            traceId =  UUID.randomUUID().toString();
            setTraceIdHoler(traceId);
            System.out.println(Thread.currentThread().getId()+ " get null & put new traceId=" + traceId);
            return traceId;
        }
        traceId = traceIdHoler.get();
        System.out.println(Thread.currentThread().getId()+ " get =" + traceId);
        return traceId;
    }

    public static void setTraceIdHoler(String traceId) {
        traceIdHoler.set(traceId);
//        System.out.println("put traceId=" + traceId);
    }
}
