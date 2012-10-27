package com.example.samplevoicerecorder;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

// MediaPlayerのサンプルアプリ
// 参考サイト：http://techbooster.jpn.org/andriod/application/267/

public class MediaPlayerSample extends Activity implements View.OnClickListener{

	TextView mTextDescritpion = null;
	private MediaPlayer mMediaPlayer;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        findViewById(R.id.button_play_stop).setOnClickListener(this);
        findViewById(R.id.button_finish).setOnClickListener(this);

        mTextDescritpion = (TextView)findViewById(R.id.text_description);

        playStart();
    }

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_play_stop:
			playStop();
			break;
		case R.id.button_finish:
			finish();
			break;
	}
	}

	private void playStart(){
		String filePath = Environment.getExternalStorageDirectory() + "/audio.3gp";

	    //ローカルファイルを再生
		mMediaPlayer = new MediaPlayer();
		try{
			mMediaPlayer.setDataSource(filePath);
			mMediaPlayer.prepare();
		}catch(Exception e ){
			e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			mMediaPlayer = null;
			finish();
			return;
		}
		mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
		mMediaPlayer.start();
	}

	private void playStop(){
		if( mMediaPlayer == null ){
			Toast.makeText(this, getString(R.string.error_not_playing), Toast.LENGTH_LONG).show();
			return;
		}

		mMediaPlayer.stop();
		mMediaPlayer = null;
        mTextDescritpion.setText(R.string.description_play_end);
	}

	// 再生完了のコールバックを受けるクラス
	private OnCompletionListener mOnCompletionListener = new OnCompletionListener() {

		public void onCompletion(MediaPlayer mp) {
			mMediaPlayer = null;
	        mTextDescritpion.setText(R.string.description_play_end);
		}
	};
}
