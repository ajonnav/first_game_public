import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MenuScreen extends GameWorld{

	private int WIDTH;
	private int HEIGHT;
	private int FRAMES_PER_SECOND;
	
	public MenuScreen(int fps, String title, int width, int height) {
		super(fps, title);
		WIDTH = width;
		HEIGHT = height;
		FRAMES_PER_SECOND = fps;
	}

	@Override
	public void initialize(Stage primaryStage, LevelChanger lC) {
		// TODO Auto-generated method stub
		levelChanger = lC;
		setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));
        setBackgroundSoccerField(getSceneNodes());
        
        Text text = new Text("Press 1 to play Level 1 \nPress 2 to play Level 2");
        text.setTranslateX(WIDTH/2 - text.getBoundsInLocal().getWidth());
        text.setTranslateY(HEIGHT/2);
        text.setFont(Font.font("Times", 50));
		text.setFill(Color.RED);
        getSceneNodes().getChildren().add(text);
        
        primaryStage.setScene(getGameSurface());
        setupInput(primaryStage);
	}

	private void setupInput(Stage s) {
		EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				// TODO Auto-generated method stub
				KeyCode eventCode = keyEvent.getCode();
				if(eventCode == KeyCode.DIGIT1) {
					levelChanger.showSplashBeforeLevelOne();
				}
				if (eventCode ==KeyCode.DIGIT2 ) {
					levelChanger.showSplashBeforeLevelTwo();
				}
			}			
		};
		s.getScene().setOnKeyPressed(keyPressHandler);
	}
}
