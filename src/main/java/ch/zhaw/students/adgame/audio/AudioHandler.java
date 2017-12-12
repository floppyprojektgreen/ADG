package ch.zhaw.students.adgame.audio;

import ch.zhaw.students.adgame.configuration.AudioConfiguration;
import ch.zhaw.students.adgame.resource.AudioLoader;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Handler for all the audio elements. <br>
 * Singleton for always having the same audio player.
 */
public class AudioHandler {
	private static AudioHandler instance;
	
	private AudioTrack currentlyPlaing;
	private MediaPlayer backgroundPlayer;
	
	private AudioHandler() {}
	
	/**
	 * Getter for the singleton instance.
	 */
	public static AudioHandler get() {
		if (instance == null) {
			instance = new AudioHandler();
		}
		return instance;
	}
	
	/**
	 * Plays the given {@link AudioTrack}. <br>
	 * If the {@link AudioTrack} is a {@link AudioType#BGM} and
	 * the currently playing one is the same it wont be changed.
	 */
	public void play(AudioTrack clip) {
		switch (clip.getType()) {
		case BGM:
			if (clip != currentlyPlaing) {
				changeBackgroundAudio(clip.getConfig());
				currentlyPlaing = clip;
			}
			break;
		case SFX:
			playSFX(clip.getConfig());
			break;
		case SILENCE:
			stopBackgroundAudio();
			break;
		default:
			break;
		}
	}
	
	private void playSFX(AudioConfiguration audioConfig) {
		AudioClip newSFX = AudioLoader.loadUncompressedAudioFile(audioConfig);
		newSFX.setCycleCount(1);
		newSFX.play();
	}

	private void changeBackgroundAudio(AudioConfiguration audioConfig) {
		stopBackgroundAudio();
		
		Media newBGM = AudioLoader.loadCompressedAudioFile(audioConfig);
		backgroundPlayer = new MediaPlayer(newBGM);
		backgroundPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		backgroundPlayer.setAutoPlay(true);
	}
	
	private void stopBackgroundAudio() {
		if (backgroundPlayer != null) {
			backgroundPlayer.stop();
			backgroundPlayer = null;
		}
	}
}
