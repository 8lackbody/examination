package com.zht.examination.utils;

import java.io.FileOutputStream;

public class OTGUtil {
    public static void setOTGEnable(boolean enabled) {
        try {
            FileOutputStream outputStream = new FileOutputStream("/sys/devices/soc/78db000.usb/dpdm_pulldown_enable");
            outputStream.write((enabled ? "otgenable" : "otgdisable").getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
