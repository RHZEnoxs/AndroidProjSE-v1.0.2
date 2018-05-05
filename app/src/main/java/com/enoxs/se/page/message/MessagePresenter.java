package com.enoxs.se.page.message;

import android.content.Context;
import android.widget.LinearLayout;

import com.enoxs.se.flow.Presenter;
import com.enoxs.se.model.event.ReqMessageCenter;
import com.enoxs.se.model.event.ResMessageCenter;
import com.enoxs.se.model.object.AppInfo;
import com.enoxs.se.page.home.HomePath;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flow.Flow;
import flow.History;

/**
 * Created by enoxs on 2018/5/4.
 */

public class MessagePresenter extends Presenter{
    private static Logger log = LoggerFactory.getLogger(MessagePresenter.class);
    public MessagePage page;
    public AppInfo appInfo;
    public ReqMessageCenter reqMessageCenter;
    public StringBuffer tempMsg = new StringBuffer(512);
    public MessagePresenter(Context context,LinearLayout pageLayout){
        setPage(context,pageLayout);
        registerEventBus();

    }
    @Override
    protected void setPage(Context context, LinearLayout pageLayout) {
        super.context = context;
        this.page = (MessagePage) pageLayout;
    }
    @Override
    public void initViewData(){
        reqMessageCenter = new ReqMessageCenter(0);
        reqMessageCenter.sendEvent();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResMessageCenter(ResMessageCenter event) {
        if(event != null){
            switch (event.getResStatus()){
                case 0:
                    appInfo = (AppInfo) event.getMsg();
                    tempMsg.setLength(0);
                    tempMsg.append("APP Message");
                    page.setTxtTitle(tempMsg.toString());
                    tempMsg.setLength(0);
                    tempMsg.append("Name : " + appInfo.getName()+"\n");
                    tempMsg.append("Version : " + appInfo.getVersion()+"\n");
                    tempMsg.append("Date : " + appInfo.getDate()+"\n");
                    tempMsg.append("Author : " + appInfo.getAuthor()+"\n");
                    tempMsg.append("Remark : " + appInfo.getRemark()+"\n");
                    page.setTxtMsg(tempMsg.toString());
                    break;
                default:
                    break;
            }
        }
    }

    public void onHome(){
        unregisterEventBus();
        Flow.get(context).setHistory(History.single(new HomePath()), Flow.Direction.REPLACE);
    }


}
