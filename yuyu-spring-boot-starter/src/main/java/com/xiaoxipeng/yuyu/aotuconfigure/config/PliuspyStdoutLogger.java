package com.xiaoxipeng.yuyu.aotuconfigure.config;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用p6spy打印sql日志
 */
@Slf4j
public class PliuspyStdoutLogger extends com.p6spy.engine.spy.appender.StdoutLogger {

    @Override
    public void logText(String text) {
        if (text.contains("SELECT")) {
           log.info(text);
        }
    }
}
