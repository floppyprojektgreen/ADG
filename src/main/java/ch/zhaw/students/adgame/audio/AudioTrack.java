package ch.zhaw.students.adgame.audio;

import ch.zhaw.students.adgame.configuration.AudioConfiguration;

/**
 * Enumeration for all usable audio clips
 */
public enum AudioTrack {
	SILENCE(null, AudioType.SILENCE),
	MAIN_THEME(AudioConfiguration.THEME_MAIN, AudioType.BGM),
	FIGHT_THEME(AudioConfiguration.THEME_FIGHT, AudioType.BGM),
	DICE_ROLL(AudioConfiguration.DICE_ROLL, AudioType.SFX),
	WILHELM_SCREAM(AudioConfiguration.WILHELM_SCREAM, AudioType.SFX);
	
	private AudioConfiguration config;
	private AudioType type;
	
	private AudioTrack(AudioConfiguration config, AudioType type) {
		this.config = config;
		this.type = type;
	}

	public AudioConfiguration getConfig() {
		return config;
	}
	
	public AudioType getType() {
		return type;
	}
}
