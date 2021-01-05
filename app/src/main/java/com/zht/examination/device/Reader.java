package com.zht.examination.device;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;

import com.zht.examination.R;
import com.zht.examination.utils.ContextApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reader {

    BaseReader baseReader = new BaseReader();

    public ReaderParameter param = new ReaderParameter();
    private volatile boolean mWorking = true;
    private volatile Thread mThread = null;
    private volatile boolean soundworking = true;
    public volatile boolean isSound = false;
    private volatile Thread sThread = null;
    public volatile int NoCardCOunt = 0;
    private Integer soundid;
    private SoundPool soundPool;
    private boolean isConnect = false;
    static HashMap<Integer, Integer> soundMap = new HashMap<>();

    Context context = ContextApplication.getAppContext();

    private List<ReadTag> mlist;
    private HashMap<String, Integer> uiData;

    private static Reader instance;

    private Reader() {
        param.ComAddr = (byte) 255;
        param.ScanTime = 2000;
        param.Session = 0;
        param.QValue = 4;
        param.TidLen = 0;
        param.TidPtr = 0;
        param.Antenna = 0x80;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
        soundMap.put(1, soundPool.load(context, R.raw.barcodebeep, 1));
        soundid = soundMap.get(1);
    }

    public static Reader getInstance() {
        if (instance == null) {
            instance = new Reader();
        }
        return instance;
    }

    public int connect() {
        int result = baseReader.Connect("/dev/ttyUSB0", 57600, 1);
        if (result == 0) {
            baseReader.PowerOn();
            SystemClock.sleep(1500);
            byte[] Ant = new byte[1];
            int code = baseReader.GetReaderInformation(new byte[2], new byte[1], new byte[1], new byte[1], new byte[1],
                    new byte[1], new byte[1], new byte[1], new byte[1], new byte[1], new byte[1], new byte[1], new byte[1]);
            if (code == 0) {
                param.ComAddr = (byte) 255;
                param.Antenna = Ant[0];
                isConnect = true;
            } else {
                isConnect = false;
                baseReader.PowerOff();
                baseReader.DisConnect();
            }
            isSound = false;
            soundworking = true;
            sThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (soundworking) {
                        if (isSound) {
                            if ((soundid == null) || (soundPool == null)) return;
                            try {
                                soundPool.play(soundid, 1, // 左声道音量
                                        1, // 右声道音量
                                        1, // 优先级，0为最低
                                        0, // 循环次数，0无不循环，-1无永远循环
                                        1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            SystemClock.sleep(30);
                        }
                    }
                }
            });
            sThread.start();
        }
        return result;
    }

    public int disConnect() {
        try {
            isSound = false;
            soundworking = false;
            sThread = null;
            isConnect = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return baseReader.DisConnect();
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void startRead() {
        if (mThread == null) {
            mWorking = true;
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    byte Target = 0;
                    while (mWorking) {
                        byte Ant = (byte) 0x80;
                        if ((param.Session == 0) || (param.Session == 1)) {
                            Target = 0;
                            NoCardCOunt = 0;
                        }
                        mlist = new ArrayList<>();
                        baseReader.Inventory_G2(param.ComAddr, (byte) param.QValue, (byte) param.Session, (byte) param.TidPtr, (byte) param.TidLen, Target, Ant, (byte) 10, mlist);
                        if (mlist.size() == 0) {
                            isSound = false;
                            if (param.Session > 1) {
                                NoCardCOunt++;
                                if (NoCardCOunt > 7) {
                                    Target = (byte) (1 - Target);
                                    NoCardCOunt = 0;
                                }
                            }
                        } else {
                            NoCardCOunt = 0;
                            onProgress(mlist);
                            isSound = false;
                        }
                    }
                    isSound = false;
                }
            });
            mThread.start();
        }
    }

    public void stopRead() {
        if (mThread != null) {
            isSound = false;
            mWorking = false;
            mThread = null;
        }
    }

    public void setPower(int Power) {
        baseReader.SetRfPower(param.ComAddr, (byte) Power);
    }


    protected void onProgress(List<ReadTag> mlist) {
        System.out.println(mlist);
        for (int p = 0; p < mlist.size(); p++) {
            ReadTag arg0 = mlist.get(p);
            String epc = arg0.epcId.toUpperCase();
            Integer times = uiData.get(epc);
            if (times == null) {
                uiData.put(epc, 1);
            } else {
                uiData.put(epc, times + 1);
            }
        }
    }

}
