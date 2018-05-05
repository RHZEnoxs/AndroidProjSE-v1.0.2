package com.enoxs.se.model.central;

import android.os.Handler;

import com.enoxs.se.model.event.MessageCenterEvent;
import com.enoxs.se.model.event.ReqMessageCenter;
import com.enoxs.se.model.event.ResMessageCenter;
import com.enoxs.se.model.object.AppInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by enoxs on 2018/5/3.
 */

public class MessageCenter {
    private static Logger log = LoggerFactory.getLogger(MessageCenter.class);
    private int reqStatus = -1;
    private static AppInfo appInfo;
    private ResMessageCenter resMessageCenter = new ResMessageCenter();
    Handler mCenterTaskHandler = new Handler();
    public MessageCenter(){
        EventBus.getDefault().register(this);
        log.info("init Message Center.");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageCenterEvent(MessageCenterEvent event) {
        if(event != null){
            appInfo = event.getAppInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReqMessageEvent(ReqMessageCenter event) {
        resMessageCenter.setResStatus(event.getReqStatus());
        reqStatus = event.getReqStatus();
        mCenterTaskHandler.post(mCenterTaskRunnable);
    }

    protected Runnable mCenterTaskRunnable = new Runnable() {
        @Override
        public void run() {
            switch (reqStatus){
                case 0:
                    resMessageCenter.setMsg(appInfo);
                    resMessageCenter.sendEvent();
                    break;
                default:
                    log.info("Request Message Event -> {} " , reqStatus);
                    break;
            }
        }
    };
}
