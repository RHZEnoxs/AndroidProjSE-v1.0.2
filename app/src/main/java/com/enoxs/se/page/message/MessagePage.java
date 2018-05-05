package com.enoxs.se.page.message;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enoxs.se.R;
import com.enoxs.se.flow.pathview.PageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.path.Path;

/**
 * Created by enoxs on 2018/5/4.
 */

public class MessagePage extends LinearLayout implements PageListener {
    private static Logger log = LoggerFactory.getLogger(MessagePage.class);
    /**
     * UI View
     */

    @BindView(R.id.txt_msg)TextView txtMsg;
    @BindView(R.id.txt_title)TextView txtTitle;
    @BindView(R.id.btn_back)Button btnBack;
    private MessagePresenter presenter;

    public MessagePage(Context context, AttributeSet attrs) {
        super(context, attrs);
        Path.get(context);
        presenter = new MessagePresenter(context,this);
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
    public void setTxtMsg(String msg){
        txtMsg.setText(msg);
    }
    public void setTxtTitle(String msg){
        txtTitle.setText(msg);
    }
    @OnClick({R.id.btn_back})
    public void onViewClick(View v){
        switch(v.getId()){
            case R.id.btn_back:
                log.debug("btnKey -> btn_back");
                presenter.onHome();
                break;
            default:
                break;
        }
    }



}
