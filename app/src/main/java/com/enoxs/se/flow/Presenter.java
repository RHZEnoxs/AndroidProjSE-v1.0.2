package com.enoxs.se.flow;

import android.content.Context;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by enoxs on 2018/5/5.
 */

public abstract class Presenter {
    protected Context context;

    protected abstract void setPage(Context context, LinearLayout pageLayout);

    protected abstract void initViewData();

    public void registerEventBus(){
        EventBus.getDefault().register(this);
    }
    public void unregisterEventBus() {
        EventBus.getDefault().unregister(this);
    }
}
