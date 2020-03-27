#include <jni.h>
#include <string>
extern "C"
JNIEXPORT void JNICALL
Java_com_example_livepusher_push_PushVideo_initPush(JNIEnv *env, jobject instance, jstring pushUrl_) {
    const char *pushUrl = env->GetStringUTFChars(pushUrl_, 0);

    // TODO

    env->ReleaseStringUTFChars(pushUrl_, pushUrl);

}extern "C"
JNIEXPORT void JNICALL
Java_com_example_livepusher_push_PushVideo_pushStop(JNIEnv *env, jobject instance) {

    // TODO

}extern "C"
JNIEXPORT void JNICALL
Java_com_example_livepusher_push_PushVideo_pushAudioData__Lbyte_3_093_2I(JNIEnv *env, jobject instance, jbyteArray data_, jint data_len) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);
    // TODO

    env->ReleaseByteArrayElements(data_, data, 0);
}extern "C"
JNIEXPORT void JNICALL
Java_com_example_livepusher_push_PushVideo_pushVideoData__Lbyte_3_093_2IZ(JNIEnv *env, jobject instance, jbyteArray data_, jint data_len, jboolean keyframe) {
    jbyte *data = env->GetByteArrayElements(data_, NULL);

    // TODO

    env->ReleaseByteArrayElements(data_, data, 0);
}extern "C"
JNIEXPORT void JNICALL
Java_com_example_livepusher_push_PushVideo_pushSPSPPS__Lbyte_3_093_2ILbyte_3_093_2I(JNIEnv *env,
                                                                                    jobject instance,
                                                                                    jbyteArray sps_,
                                                                                    jint sps_len,
                                                                                    jbyteArray pps_,
                                                                                    jint pps_len) {
    jbyte *sps = env->GetByteArrayElements(sps_, NULL);
    jbyte *pps = env->GetByteArrayElements(pps_, NULL);

    // TODO

    env->ReleaseByteArrayElements(sps_, sps, 0);
    env->ReleaseByteArrayElements(pps_, pps, 0);
}