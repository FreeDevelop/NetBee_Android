package com.example.netbee2x;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class NetBee_SoundTip 
{
	///< 网络状态提示音
	private MediaPlayer mPlayer = null; 
	///< 使用者
	private Context user_Context = null;
	
	public NetBee_SoundTip(Context context) 
	{
		// TODO Auto-generated constructor stub
		user_Context = context;
		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
		{		
			@Override
			public void onCompletion(MediaPlayer mp) 
			{
				// TODO Auto-generated method stub
				if (null != mPlayer)
				{
					Log.i("screen_direction", "LLLLL realease....");
					mPlayer.release();
				}
			}
		});
	}

	public void netTipPlay(int resid)
	{
		mPlayer.reset();
		mPlayer = MediaPlayer.create(user_Context, resid);
		try {
			///< 在播放之前先判断playerMusic是否被占用，这样就不会报错了!!!
			if (null != mPlayer) 
			{
				mPlayer.stop();
			}
			mPlayer.prepare();
			mPlayer.start();
			Log.i("screen_direction", "LLLLL start....");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void netTipStop()
	{
		if (mPlayer.isPlaying())
		{
			mPlayer.pause();
		}
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
	
	public boolean netTipIsPlaying()
	{
		Log.i("screen_direction", "LLLLL isPlay...." + mPlayer.isPlaying());
		return mPlayer.isPlaying();
	}
}
