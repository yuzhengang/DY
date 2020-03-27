//
// Created by admin on 2020/3/17.
//

#ifndef LIVEPUSHER_RTMPPUSH_H
#define LIVEPUSHER_RTMPPUSH_H

#include <malloc.h>
#include <string.h>
#include "Queue.h"
#include "pthread.h"
#include "CallJava.h"

extern "C"{
    #include "librtmp/rtmp.h"
};

class RtmpPush{

public:
    RTMP  *rtmp=NULL;
    char  *url=NULL;
    Queue  *queue=NULL;
    pthread_t  push_thread;
    CallJava *callJava=NULL;
    bool  startPushing= false;
    long  startTime=0;
public:
    RtmpPush(const char *url, CallJava *callJava);

    ~RtmpPush();

    void init();

    void pushSPSPPS(char *sps, int sps_len, char *pps, int pps_len);

    void pushVideoData(char *data, int data_len, bool keyframe);

    void pushAudioData(char *data, int data_len);

    void pushStop();
};

#endif //LIVEPUSHER_RTMPPUSH_H
