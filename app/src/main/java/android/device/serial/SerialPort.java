package android.device.serial;

import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {
	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method
	 * close();
	 */
	private FileDescriptor mFd;
	private FileInputStream mFileInputStream;
	private FileOutputStream mFileOutputStream;
	String path = Build.VERSION.SDK_INT == 22 ? "/dev/ttyHSL1"
			: "/dev/ttyHSL0";

	public SerialPort() {
	}
	/**
	 * @param speed 波特率 300 <= speed <= 460800
	 * @return 0 成功 小于 0 失败 -2 参数错误
	 * @throws SecurityException
	 * @throws IOException
	 */
	public int open(int speed) throws SecurityException, IOException {
		File device = new File(path);
		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			throw new SecurityException();
		}

		mFd = native_open(device.getAbsolutePath(), speed, 0);
		if (mFd == null) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
		return 0;
	}
	/**
	 * 
	 * @param speed 波特率 300 <= speed <= 460800
	 * @param event 校验位 o e n
	 * @param bits 5 < bits < 8
	 * @param stop 停止位 1 or 2
	 * @return 0 成功 小于 0 失败 -2 参数错误
	 * @throws SecurityException
	 * @throws IOException
	 */
	public int open(int speed, char event, int bits, int stop) throws SecurityException, IOException {
		File device = new File(path);
		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			throw new SecurityException();
		}
        if(300 > speed && speed > 460800) {
            return -2;
        }
        if(event != 'O' && event != 'o' && event != 'E' && event != 'e' && event != 'N' && event != 'n'){
        	return -2;
        }
        if(bits < 5 || bits > 8) {
        	return -2;
        }
        if(stop < 1 || stop > 2) {
        	return -2;
        }
		mFd = nativeSerialPortOpen(device.getAbsolutePath(), speed, event, bits, stop);
		if (mFd == null) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}
		mFileInputStream = new FileInputStream(mFd);
		mFileOutputStream = new FileOutputStream(mFd);
		return 0;
	}
	public int open(String deviceName, int speed, char event, int bits, int stop) throws SecurityException, IOException {
        File device = new File(deviceName);
        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            throw new SecurityException();
        }
        if(300 > speed && speed > 460800) {
            return -2;
        }
        if(event != 'O' && event != 'o' && event != 'E' && event != 'e' && event != 'N' && event != 'n'){
            return -2;
        }
        if(bits < 5 || bits > 8) {
            return -2;
        }
        if(stop < 1 || stop > 2) {
            return -2;
        }
        mFd = nativeSerialPortOpen(device.getAbsolutePath(), speed, event, bits, stop);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
        return 0;
    }
	public int close() {
		return native_close_fd(mFd);
	}
	public int setFlowcontrol(int flow) {
        return native_setFlowcontrol(flow);
    }

	public int SetDTRPinLevel(int level){
		return native_SetDTRLevel(level);
	}
	public int SetRTSPinLevel(int level){
		return native_SetRTSLevel(level);
	}

	public int GetDTRPinLevel(){
			return native_GetDTRLevel();
		}
	
	public int GetRTSPinLevel(){
			return native_GetRTSLevel();
		}
	
	// Getters and setters
	public InputStream getInputStream() {
		return mFileInputStream;
	}

	public OutputStream getOutputStream() {
		return mFileOutputStream;
	}

	// JNI
	public native static FileDescriptor native_open(String path, int baudrate,
                                                    int flags);
	public native FileDescriptor nativeSerialPortOpen(String deviceName, int speed, char event, int bits, int stop);
	public native int native_close();
	public native int native_close_fd(FileDescriptor fd);
	public native int native_setFlowcontrol(int flow);
	public native int native_SetDTRLevel(int level);
	public native int native_SetRTSLevel(int level);
	public native int native_GetDTRLevel();
	public native int native_GetRTSLevel();
	
	static {
		System.loadLibrary("deviceSerial");
	}
}
