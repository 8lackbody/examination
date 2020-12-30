package com.zht.examination.device;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class MessageTran {
	private InputStream mInStream = null;
	private OutputStream mOutStream = null;
	private SerialPort mSerialPort = null;
	private boolean connected=false;
    public boolean isOpen()
    {
    	return connected;
    }
	public int open(String ComPort, int BaudRate)
	{
		int result=0x30;
		try {
			//mSerialPort = new SerialPort(new File(ComPort), BaudRate, 0);
			mSerialPort = new SerialPort();
			 result = mSerialPort.open(ComPort, BaudRate, 'N', 8, 1);

		} catch (SecurityException e) {
			;
		} catch (IOException e) {
			;
		} catch (InvalidParameterException e){
			;
		}
		if((mSerialPort!=null)&&(result==0))
		{
            mSerialPort.SetDTRPinLevel(1);
            mSerialPort.SetRTSPinLevel(1);
			mInStream =  mSerialPort.getInputStream();
			mOutStream = mSerialPort.getOutputStream();
			connected=true;
			return 0;
		}
		else
		{
			return -1;
		}
		
	}
	
	public int close()
	{
		if(mInStream!=null)
		{
			try {
				mInStream.close();
				mOutStream.close();
				mSerialPort.close();
				mSerialPort=null;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		connected=false;
		return 0;
	}
	public void PowerOn()
	{
		if(mSerialPort!=null)
		{
			mSerialPort.SetDTRPinLevel(1);
			mSerialPort.SetRTSPinLevel(1);
		}

	}
	public void PowerOff()
	{
		if(mSerialPort!=null)
		{
			mSerialPort.SetDTRPinLevel(0);
			mSerialPort.SetRTSPinLevel(0);
		}

	}
	public byte[] Read()
	{
		if(!connected)return null;
		try {
			byte[]RecvBuff = new byte[2560];
			int len = mInStream.read(RecvBuff);
			if(len>0)
			{
				byte[]buff=new byte[len];
				System.arraycopy(RecvBuff, 0, buff, 0, len);
				Log.d("Recv", bytesToHexString(buff,0,buff.length));
				return buff;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int Write(byte[]buffer)
	{
		if(!connected)return -1;
		if(buffer.length != ((buffer[0]&255) + 1))
		{
			return -1;
		}
		try {
			byte[]cmd = new byte[(buffer[0]&255) + 1];
			System.arraycopy(buffer, 0, cmd, 0, cmd.length);
			mOutStream.write(cmd);
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public String bytesToHexString(byte[] src, int offset, int length) {
		StringBuilder stringBuilder = new StringBuilder("");
		try{
			if (src == null || src.length <= 0) {
				return null;
			}
			for (int i = offset; i < length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() == 1) {
					stringBuilder.append(0);
				}
				stringBuilder.append(hv);
			}
			return stringBuilder.toString().toUpperCase();
		}catch(Exception ex)
		{ return null;}
	}

	public byte[] hexStringToBytes(String hexString) {
		try{
			if (hexString == null || hexString.equals("")) {
				return null;
			}
			hexString = hexString.toUpperCase();
			int length = hexString.length() / 2;
			char[] hexChars = hexString.toCharArray();
			byte[] d = new byte[length];
			for (int i = 0; i < length; i++) {
				int pos = i * 2;
				d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
			}
			return d;
		}catch(Exception ex)
		{return null;}
	}

	private byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
