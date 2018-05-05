package com.enoxs.se.model.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by enoxs on 2018/5/3.
 */

public class ReqMessageCenter {
    private static int reqStatus = -1;
    public ReqMessageCenter(int reqStatus){
        this.reqStatus = reqStatus;
    }
    public int getReqStatus(){
        return this.reqStatus;
    }
    public void sendEvent() {
        EventBus.getDefault().post(this);
    }
}
