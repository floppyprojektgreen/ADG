package ch.zhaw.students.adgame.configuration;

import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Enumeration with {@link ExtensionFilter} for filtering in a file system. <br>
 * Not linked with any configuration file.
 */
public enum FileExtenstionConfiguration {
	ADGAME_SAVE("ADGame Save", "*.adg");
	
	private ExtensionFilter filter;
	
	private FileExtenstionConfiguration(String name, String fileExtension) {
		filter = new ExtensionFilter("ADGame Save", "*.adg");
	}
	
	/**
	 * Get the corresponding extension filter.
	 */
	public ExtensionFilter getFilter() {
		return filter;
	}
}
