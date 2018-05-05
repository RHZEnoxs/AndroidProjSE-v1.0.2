package com.enoxs.se.model.event;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Objects;

/**
 * Created by enoxs on 2018/5/3.
 */

public class ResMessageCenter {
    private int resStatus = -1;
    private List<Object> lstMsg;
    private Object msg;
    public ResMessageCenter(){
    }
    public ResMessageCenter(int resStatus){
        this.resStatus = resStatus;
    }
    public void setResStatus(int resStatus){
        this.resStatus = resStatus;
    }
    public int getResStatus(){
        return resStatus;
    }

    public void setLstMsg(List<Object> lstMsg){
        this.lstMsg = lstMsg;
    }
    public void setMsg(Object msg){
        this.msg = msg;
    }
    public List<Object> getLstMsg(){
        return lstMsg;
    }
    public Object getMsg(){
        return msg;
    }

    public void sendEvent() {
        EventBus.getDefault().post(this);
    }
}
