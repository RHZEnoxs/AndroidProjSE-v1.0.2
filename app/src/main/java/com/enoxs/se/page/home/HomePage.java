package com.enoxs.se.page.home;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.enoxs.se.R;
import com.enoxs.se.flow.pathview.PageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.path.Path;

/**
 * Created by wu on 2015/08/25
 */
public class HomePage extends LinearLayout implements PageListener {
    private static Logger log = LoggerFactory.getLogger(HomePage.class);
    /**
     * UI View
     */
    @BindView(R.id.btn_sample)Button btnSample;
    @BindView(R.id.btn_message)Button btnMessage;
    @BindView(R.id.btn_crash)Button btnCrash;
    private HomePresenter presenter;

    public HomePage(Context context, AttributeSet attrs) {
        super(context, attrs);
        Path.get(context);
        presenter = new HomePresenter(context,this);
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
//        Button btn = (Button) findViewById(R.id.btn_do);
    }

    private void assignEvent() {
        /*btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegisterClick();
            }
        });*/
    }
    @OnClick({R.id.btn_message,R.id.btn_sample,R.id.btn_crash})
    public void onViewClick(View v){
        switch(v.getId()){
            case R.id.btn_message:
                log.debug("btnKey -> btn_message");
                presenter.onMessage();
                break;
            case R.id.btn_sample:
                log.debug("btnKey -> btn_sample");
                presenter.onSample();
                break;
            case R.id.btn_crash:
                log.debug("btnKey -> btn_crash");
                throw new RuntimeException("Crash ha !");
//                break;
            default:
                break;
        }
    }
}
