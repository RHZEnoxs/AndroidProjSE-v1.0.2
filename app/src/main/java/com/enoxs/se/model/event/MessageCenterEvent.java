package com.enoxs.se.model.event;

import com.enoxs.se.model.object.AppInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by enoxs on 2018/5/3.
 */

public class MessageCenterEvent {
    AppInfo appInfo;
    public MessageCenterEvent(){

    }
    /*public MessageCenterEvent(Object object,int which){

    }*/

    public void setAppInfo(AppInfo appInfo){
        this.appInfo = appInfo;
    }
    public AppInfo getAppInfo(){
        return this.appInfo;
    }
    public void sendMessageEvent() {
        EventBus.getDefault().post(this);
    }
}
