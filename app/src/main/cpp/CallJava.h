//
// Created by admin on 2020/3/13.
//
#include <cwchar>
#include "jni.h"
#ifndef LIVEPUSHER_CALLJAVA_H
#define LIVEPUSHER_CALLJAVA_H

#define  THREAD_MAIN  1
#define  THREAD_CHILD 2

class CallJava{

public:
    JNIEnv *jniEnv=NULL;
    JavaVM *javaVM=NULL;
    jobject  jobj;

    jmethodID jmid_connecting;
    jmethodID jmid_connectsuccess;
    jmethodID jmid_connectfail;

public:
    CallJava(JavaVM  *javaVM,JNIEnv *jniEnv,jobject *jobj);

    ~CallJava();

    void onConnectint(int type);

    void onConnectsuccess();

    void onConnectFail(char *msg);
};
#endif //LIVEPUSHER_CALLJAVA_H

















