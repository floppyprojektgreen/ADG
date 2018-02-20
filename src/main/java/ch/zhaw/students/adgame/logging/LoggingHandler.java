package ch.zhaw.students.adgame.logging;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import ch.zhaw.students.adgame.configuration.SystemConfiguration;

public class LoggingHandler {
	private static final Logger LOGGER = Logger.getGlobal();
	
	static {
		try {
			LOGGER.setLevel(Level.SEVERE);
			
			File file = new File("log");
			if (!file.exists()) {
				file.mkdir();
			}
			
			FileHandler fh = new FileHandler("log/adg-%u.%g.log", 5_000_000, 10);
			fh.setFormatter(new SimpleFormatter());
			
			LOGGER.addHandler(fh);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		if (SystemConfiguration.isSet(SystemConfiguration.DEV_ENV)) {
			LOGGER.setLevel(Level.INFO);
		}
	}
	
	public static void log(String msg, Level level) {
		LOGGER.log(level, msg);
	}
	
	public static void log(Throwable t, Level level) {
		MessageBuilder mb = new MessageBuilder(System.out);
		t.printStackTrace(mb);
		log(mb.getMessage(), level);
	}
	
	public static void severe(String msg) {
		log(msg, Level.SEVERE);
	}
	
	public static void warning(String msg) {
		log(msg, Level.WARNING);
	}
	
	public static void info(String msg) {
		log(msg, Level.INFO);
	}
}
