package com.iplay.iplaychat.service.xmpp;

/**
 * Created by cheergoivan on 2017/7/31.
 */

public enum XMPPCommand {
    CONNECT("connect");

    private String command;

    private XMPPCommand(String command){
        this.command = command;
    }



}
