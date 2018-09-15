package br.uff.mh.mestrado.utils;

import java.io.File;

import javafx.scene.media.AudioClip;

public class SoundUtils {
	private static final SoundUtils instance = new SoundUtils();
	private AudioClip clip;

	private SoundUtils() {
		clip = new AudioClip(new File("/Users/Wil/Downloads/Welcome To The Jungle.mp3").toURI().toString());
	}

	public static SoundUtils getInstance() {
		return instance;
	}

	public void play() {
		stop();
		clip.play();
	}

	public void stop() {
		try {
			clip.stop();
		} catch (Exception e) {
		}
	}
}
