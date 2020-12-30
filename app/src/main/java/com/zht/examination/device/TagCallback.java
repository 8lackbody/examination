package com.zht.examination.device;

public interface TagCallback {

    void tagCallback(ReadTag tag);

    int tagCallbackFailed(int reason);

}
