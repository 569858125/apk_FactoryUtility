package com.datamax.factorytest.bluetooth;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import com.datamax.factorytest.utils.Logcat;

/**
 * 创建者      xiekang
 * 创建时间    2019-7-8
 * 描述        ${DESCRIPTION}
 * <p>
 * 更新者      xiekang
 * <p>
 * 更新时间    2019-7-8
 * 更新描述    ${DESCRIPTION}
 */
public class RecordPlayThread extends Thread {

    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private int recBufSize;
    
    public RecordPlayThread() {
        int bufferSize = AudioTrack.getMinBufferSize(8000,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                8000,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize,
                AudioTrack.MODE_STREAM);
        recBufSize = AudioRecord.getMinBufferSize(8000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, recBufSize);
        Log.e("lee", "buffer");
    }

    @Override
	public void run() {
        try {
            byte[] buffer = new byte[recBufSize];
            audioRecord.startRecording();//开始录制
            audioTrack.play();//开始播放

            while (!interrupted()) {
                //从MIC保存数据到缓冲区
                int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);

                byte[] tmpBuf = new byte[bufferReadResult];
                System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
                //写入数据即播放
                audioTrack.write(tmpBuf, 0, tmpBuf.length);
            }
            audioTrack.stop();
            audioRecord.stop();
        } catch (Throwable t) {
            Logcat.e(t.toString());
        }
    }
}
