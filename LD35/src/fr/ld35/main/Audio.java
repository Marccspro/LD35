package fr.ld35.main;

import javax.sound.sampled.*;

public class Audio {
	public static Audio avaler = loadSound("/audio/avaler.wav");
	public static Audio beep = loadSound("/audio/beep.wav");
	public static Audio bitTimer = loadSound("/audio/bipTimer.wav");
	public static Audio music = loadSound("/audio/music.wav");
	public static Audio trash = loadSound("/audio/trash2.wav");

	public static Audio loadSound(String fileName) {
		Audio sound = new Audio();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Audio.class.getResource(fileName));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			System.out.println(e);
		}
		return sound;
	}

	private Clip clip;

	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}