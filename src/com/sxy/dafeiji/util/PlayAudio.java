package com.sxy.dafeiji.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

public class PlayAudio extends Thread{
	
	//音乐文件名
	String musicName;
	//是否循环播放
	boolean isLoop;
	//播放对象
	AudioClip clip;
	
	public PlayAudio(String musicName,boolean isLoop) {//是否循环
		this.musicName=musicName;
		this.isLoop=isLoop;
	}
	
	/**
	 * 关闭当前线程中的音乐
	 */
	public void stopMusic(){
		clip.stop();
	}
	
	public void playMusic(){
		clip.play();
	}
	
	@Override
	public void run() {
		try {
			File musicFile=new File(musicName);
			URL url=musicFile.toURI().toURL();
			//通过URL得到一个播放对象
			clip=Applet.newAudioClip(url);
			//播放
			clip.play();
			//是否循环
			if(isLoop){
				clip.loop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
