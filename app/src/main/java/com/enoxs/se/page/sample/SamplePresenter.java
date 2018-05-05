package com.enoxs.se.page.sample;

import android.content.Context;
import android.widget.LinearLayout;

import com.enoxs.se.flow.Presenter;

/**
 * Created by enoxs on 2018/5/4.
 */

public class SamplePresenter extends Presenter{
    SamplePage page;
    public SamplePresenter(Context context,LinearLayout pageLayout) {
        setPage(context,pageLayout);
    }

    @Override
    protected void setPage(Context context, LinearLayout pageLayout) {
        super.context = context;
        this.page = (SamplePage) pageLayout;
    }

    @Override
    protected void initViewData() {
        page.setTxtMessage("Copy Page,Path,Presenter");
    }
}
