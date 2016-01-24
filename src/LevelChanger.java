import javafx.stage.Stage;


public class LevelChanger {
	private Stage stage;
	private final int BOUNDS =5;
	private int WIDTH;
	private int HEIGHT;
	private int FRAMES_PER_SECOND;
	public LevelChanger(Stage s, int fps, int width, int height) {
		stage = s;
		WIDTH =width;
		HEIGHT= height;
		FRAMES_PER_SECOND =fps;
	}
	
	public void showMenuScreen() {
		GameWorld menuScreen = new MenuScreen(FRAMES_PER_SECOND, "Menu Screen", WIDTH, HEIGHT);
		menuScreen.initialize(stage, this);
	       // ((LevelTwo)myGame).introduceRonaldo();
	        // kick off the game loop
		menuScreen.beginGameLoop();
	        
	        stage.setTitle(menuScreen.getWindowTitle());
	        stage.setMinHeight(HEIGHT);
	        stage.setMaxHeight(HEIGHT);
	        stage.setMinWidth(WIDTH);
	        stage.setMaxWidth(WIDTH);
	        stage.show();
	}
	
	public void playLevelOne() {

		stage.hide();
    	GameWorld levelOne = new LevelOne(FRAMES_PER_SECOND, "Level 1", WIDTH, HEIGHT);
    	// setup title, scene, stats, controls, and actors.
        levelOne.initialize(stage, this);
       // ((LevelTwo)myGame).introduceRonaldo();
        // kick off the game loop
        levelOne.beginGameLoop();
        
        stage.setTitle(levelOne.getWindowTitle());
        stage.setMinHeight(HEIGHT);
        stage.setMaxHeight(HEIGHT);
        stage.setMinWidth(WIDTH);
        stage.setMaxWidth(WIDTH);
        stage.show();
	}
	
	public void playLevelTwo() {

		stage.hide();
    	GameWorld levelTwo = new LevelTwo(FRAMES_PER_SECOND, "Level 2", WIDTH, HEIGHT);
    	// setup title, scene, stats, controls, and actors.
        levelTwo.initialize(stage, this);
       // ((LevelTwo)myGame).introduceRonaldo();
        // kick off the game loop
        levelTwo.beginGameLoop();
        
        stage.setTitle(levelTwo.getWindowTitle());
        stage.setMinHeight(HEIGHT);
        stage.setMaxHeight(HEIGHT);
        stage.setMinWidth(WIDTH);
        stage.setMaxWidth(WIDTH);
        stage.show();
	}
	
	public void showSplashBeforeLevelOne() {
		stage.hide();
		showSplashScreen("LEVEL 1");
	}
	
	public void showSplashBeforeLevelTwo() {
		stage.hide();
		showSplashScreen("LEVEL 2");
	}
	
	public void showSplashScreen(String message) {
		GameWorld splash = new SplashScreen(FRAMES_PER_SECOND, message, WIDTH, HEIGHT);
    	// setup title, scene, stats, controls, and actors.
        splash.initialize(stage, this);
       // ((LevelTwo)myGame).introduceRonaldo();
        // kick off the game loop
        splash.beginGameLoop();
        
        stage.setTitle(splash.getWindowTitle());
        stage.setMinHeight(HEIGHT);
        stage.setMaxHeight(HEIGHT);
        stage.setMinWidth(WIDTH);
        stage.setMaxWidth(WIDTH);
        stage.show();
	}
	
	public void showLosingScreen() {
	}
	
	public void showWinningScreen() {
	}
}
