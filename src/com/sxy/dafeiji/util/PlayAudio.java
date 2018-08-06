package com.sxy.dafeiji.util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

public class PlayAudio extends Thread{
	
	//�����ļ���
	String musicName;
	//�Ƿ�ѭ������
	boolean isLoop;
	//���Ŷ���
	AudioClip clip;
	
	public PlayAudio(String musicName,boolean isLoop) {//�Ƿ�ѭ��
		this.musicName=musicName;
		this.isLoop=isLoop;
	}
	
	/**
	 * �رյ�ǰ�߳��е�����
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
			//ͨ��URL�õ�һ�����Ŷ���
			clip=Applet.newAudioClip(url);
			//����
			clip.play();
			//�Ƿ�ѭ��
			if(isLoop){
				clip.loop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
