import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This is the main program, it is basically boilerplate to create
 * an animated scene.
 * 
 * @author Anirudh Jonnavithula
 */
public class Main extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = (int) Screen.getPrimary().getVisualBounds().getHeight();
    private final int FRAMES_PER_SECOND = 60;
    private final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private int level = 0;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        // create your own game here
    	
    	LevelChanger levelChanger = new LevelChanger(s, FRAMES_PER_SECOND, WIDTH, HEIGHT);
    	levelChanger.showMenuScreen();    	
        
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
    
    private int getScreenHeight() {
    	Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    	return (int)primaryScreenBounds.getHeight();
    }
}
