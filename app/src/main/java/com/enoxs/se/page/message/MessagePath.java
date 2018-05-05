package com.enoxs.se.page.message;

import com.enoxs.se.R;
import com.enoxs.se.flow.pathview.Layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flow.path.Path;

/**
 * Created by enoxs on 2018/5/4.
 */
@Layout(R.layout.page_message)
public class MessagePath extends Path {
    private static Logger log = LoggerFactory.getLogger(MessagePath.class);
    public MessagePath(){
        log.info("init MessagePath.");
    }
}
