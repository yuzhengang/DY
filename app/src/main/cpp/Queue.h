//
// Created by admin on 2020/3/17.
//

#ifndef LIVEPUSHER_QUEUE_H
#define LIVEPUSHER_QUEUE_H
#include "queue"
#include "pthread.h"
#include "AndroidLog.h"
extern "C"
{
   #include "librtmp/rtmp.h"
};

class Queue{

public:
    std::queue<RTMPPacket *>   queuePacket;

    pthread_mutex_t mutexPacket;

    pthread_cond_t  condPacket;
public:

    Queue( );

    ~Queue();

    int putRtmpPacket(RTMPPacket *packet);

    RTMPPacket* getRtmpPacket();

    void clearQueue();

    void notifyQueue();
};
#endif //LIVEPUSHER_QUEUE_H
