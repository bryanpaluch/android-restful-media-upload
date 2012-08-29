package com.example.android.skeletonapp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.media.MediaRecorder;
import android.os.ParcelFileDescriptor;
import android.util.Log;

public class Transmitter {

	private MediaRecorder mRecorder = null;
	private static final String LOG_TAG = "AudioRecordTest";
	
	 public void onRecord(boolean start) {
	        if (start) {
	            startRecording();
	        } else {
	            stopRecording();
	        }
	    }
	 
	
	@SuppressLint("NewApi")
	private void startRecording() {
		 Socket soc = null;
		try {
			soc = new Socket("192.168.1.134",80);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
	
			e1.printStackTrace();
		}
		 ParcelFileDescriptor pfd = ParcelFileDescriptor.fromSocket(soc);
		 
		 	OutputStream os = null;
		 	try {
				 os = soc.getOutputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 	StringBuffer stringBuf = new StringBuffer();
		 	stringBuf.append("POST /api/shout/2000 HTTP/1.0\r\n");
		 	stringBuf.append("header1: value1\r\n");
		 	stringBuf.append("\r\n");
		 	//stringBuf.append("BLAHBLAHB LAHB ALH");
		 	try {
				os.write(stringBuf.toString().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(pfd.getFileDescriptor());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

	
	
	
	
	
	
}
