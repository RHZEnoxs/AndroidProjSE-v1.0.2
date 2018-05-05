package com.enoxs.se.page.sample;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enoxs.se.R;
import com.enoxs.se.flow.pathview.PageListener;
import com.enoxs.se.page.message.MessagePage;
import com.enoxs.se.page.message.MessagePresenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.path.Path;

/**
 * Created by enoxs on 2018/5/4.
 */

public class SamplePage extends LinearLayout implements PageListener {
    private static Logger log = LoggerFactory.getLogger(SamplePage.class);
    private SamplePresenter presenter;
    @BindView(R.id.txt_message)TextView txtMessage;
    public SamplePage(Context context, AttributeSet attrs) {
        super(context, attrs);
        Path.get(context);
        presenter = new SamplePresenter(context,this);
        log.info("init SamplePage.");
    }

    @Override
    public void onPageStart(Activity activity) {
        ButterKnife.bind(this, activity);
        assignViews();
        assignEvent();
    }

    @Override
    public void onCreateOptionsMenu(Activity activity, Menu menu) {
        activity.setTitle(this.getClass().getSimpleName());
    }

    private void assignViews() {
        presenter.initViewData();
    }

    private void assignEvent() {

    }
    public void setTxtMessage(String msg){
        txtMessage.setText(msg);
    }
}
