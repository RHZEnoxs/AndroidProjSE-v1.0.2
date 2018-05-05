package com.enoxs.se;

import android.app.Application;

import com.enoxs.se.utillity.CrashHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by enoxs on 2018/4/22.
 */

public class AppMain extends Application {
    private static Logger log;
    public void onCreate(){
        super.onCreate();
        log = LoggerFactory.getLogger("AppMain");
        log.info("AppMain -> init.");
        init();
    }
    private void init(){
        initCrashLog();
    }
    private void initCrashLog() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
