package com.example.samplevoicerecorder;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// VoiceRecorderのサンプルアプリ
// 参考サイト：http://techbooster.jpn.org/andriod/multimedia/2109/

public class MainActivity extends Activity implements View.OnClickListener{

	private MediaRecorder mMediaRecorder = null;

	private TextView mTextDescritpion = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_rec_start).setOnClickListener(this);
        findViewById(R.id.button_rec_stop).setOnClickListener(this);
        findViewById(R.id.button_play_start).setOnClickListener(this);

        mTextDescritpion = (TextView)findViewById(R.id.text_description);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		switch(v.getId()){
			case R.id.button_rec_start:
				recStart();
				break;
			case R.id.button_rec_stop:
				recStop();
				break;
			case R.id.button_play_start:
				{
					Intent intent = new Intent(this, MediaPlayerSample.class);
					startActivity(intent);
				}
				break;
		}
	}

	private void recStart(){
		mMediaRecorder = new MediaRecorder();
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        //保存先
        String filePath = Environment.getExternalStorageDirectory() + "/audio.3gp";
        mMediaRecorder.setOutputFile(filePath);

        //録音準備＆録音開始
        try {
        	mMediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            mMediaRecorder = null;
			return;
		}
        mMediaRecorder.start();   //録音開始
        mTextDescritpion.setText(R.string.description_recording);
	}


	private void recStop(){

		if( mMediaRecorder == null ){
			Toast.makeText(this, getString(R.string.error_not_recording), Toast.LENGTH_LONG).show();
			return;
		}

		mMediaRecorder.stop();
		mMediaRecorder.reset();   //オブジェクトのリセット
	    //release()前であればsetAudioSourceメソッドを呼び出すことで再利用可能
		mMediaRecorder.release(); //Recorderオブジェクトの解放
		mMediaRecorder = null;
        mTextDescritpion.setText(R.string.description_recorded);
	}


}
