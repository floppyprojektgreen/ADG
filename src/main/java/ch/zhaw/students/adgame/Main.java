package ch.zhaw.students.adgame;

import ch.zhaw.students.adgame.configuration.SystemConfiguration;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.CacheLoader;
import ch.zhaw.students.adgame.ui.UserInterface;
import ch.zhaw.students.adgame.ui.WindowHandler;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the Application.<br>
 * Loads and initializes the first needed information for the application to
 * start.
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		if (SystemConfiguration.isSet(SystemConfiguration.PRE_CACHING)) {
			CacheLoader.initializeAllCachesInLoaders();
		}
		
		WindowHandler.get().initialize(primaryStage);
		WindowHandler.get().openUI(UserInterface.MAIN_MENU);

		primaryStage.setTitle("AD Game");
		primaryStage.show();
	}

	/**
	 * Launches the javafx application.
	 */
	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			LoggingHandler.log(e);
		}
	}
}
