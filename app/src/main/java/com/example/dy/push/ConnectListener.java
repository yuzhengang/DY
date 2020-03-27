package com.example.dy.push;

public interface ConnectListener {

    void onConnecting();

    void onConnectSuccess();

    void onConnectFail(String msg);

}
