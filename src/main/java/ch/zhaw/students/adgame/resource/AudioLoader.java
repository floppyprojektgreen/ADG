package ch.zhaw.students.adgame.resource;

import java.io.File;

import ch.zhaw.students.adgame.configuration.AudioConfiguration;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

/**
 * Loader for audio clips used by the application.
 */
public class AudioLoader {
	/**
	 * Loads and returns a compressed Media file. Saver variant for long and large sound files.
	 * @see AudioLoader#loadUncompressedAudioFile(AudioConfiguration)
	 */
	public static Media loadCompressedAudioFile(AudioConfiguration audioConfiguration) {
		return new Media(new File(AudioConfiguration.getConfiguration(audioConfiguration)).toURI().toString());
	}
	
	/**
	 * Loads and returns a raw and uncompressed {@link AudioClip}. Not really suited for long and large audio files.
	 * @see AudioLoader#loadCompressedAudioFile(AudioConfiguration)
	 */
	public static AudioClip loadUncompressedAudioFile(AudioConfiguration audioConfiguration) {
		return new AudioClip(new File(AudioConfiguration.getConfiguration(audioConfiguration)).toURI().toString());
	}
}
