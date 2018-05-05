package com.enoxs.se.page.home;

import android.content.Context;
import android.widget.LinearLayout;

import com.enoxs.se.flow.Presenter;
import com.enoxs.se.page.message.MessagePath;
import com.enoxs.se.page.sample.SamplePath;

import flow.Flow;
import flow.History;

/**
 * Created by wu on 2015/08/25
 */
public class HomePresenter extends Presenter{
    HomePage page;
    public HomePresenter(Context context, LinearLayout pageLayout) {
        setPage(context,pageLayout);
    }

    public void onSample() {
        Flow.get(context).set(new SamplePath());
//        Flow.get(context).setHistory(History.single(new SamplePath()), Flow.Direction.REPLACE);
    }

    public void onMessage() {
//        Flow.get(context).set(new MessagePath());
        Flow.get(context).setHistory(History.single(new MessagePath()), Flow.Direction.REPLACE);
    }

    @Override
    protected void setPage(Context context, LinearLayout pageLayout) {
        super.context = context;
        this.page = (HomePage) pageLayout;
    }

    @Override
    protected void initViewData() {

    }
}
