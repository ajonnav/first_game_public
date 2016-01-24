import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
 
/**
 * This application demonstrates a JavaFX 2.x Game Loop.
 * Shown below are the methods which comprise of the fundamentals to a
 * simple game loop in JavaFX:
*
 *  <strong>initialize()</strong> - Initialize the game world.
 *  <strong>beginGameLoop()</strong> - Creates a JavaFX Timeline object containing the game life cycle.
 *  <strong>updateSprites()</strong> - Updates the sprite objects each period (per frame)
 *  <strong>checkCollisions()</strong> - Method will determine objects that collide with each other.
 *  <strong>cleanupSprites()</strong> - Any sprite objects needing to be removed from play.
 *
 * @author cdea
 */
public abstract class GameWorld {
	
	protected int WIN =0;
	
	protected int LOSE =1;
		
	/** The object that handles changing levels **/
	protected LevelChanger levelChanger;
 
    /** The JavaFX Scene as the game surface */
    private Scene gameSurface;
    /** All nodes to be displayed in the game window. */
    private Group sceneNodes;
    /** The game loop using JavaFX's <code>Timeline</code> API.*/
    private static Timeline gameLoop;
 
    /** Number of frames per second. */
    private final int framesPerSecond;
 
    /** Title in the application window.*/
    private final String windowTitle;
 
    /**
     * The sprite manager.
     */
    private final SpriteManager spriteManager = new SpriteManager();
 
    /**
     * Constructor that is called by the derived class. This will
     * set the frames per second, title, and setup the game loop.
     * @param fps - Frames per second.
     * @param title - Title of the application window.
     */
    public GameWorld(final int fps, final String title) {
        framesPerSecond = fps;
        windowTitle = title;
        // create and set timeline for the game loop
        buildAndSetGameLoop();
    }
 
    /**
     * Builds and sets the game loop ready to be started.
     */
    protected void buildAndSetGameLoop() {
 
        final Duration oneFrameAmt = Duration.millis(1000/getFramesPerSecond());
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
            new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {			
					
                    
					// update actors
                    updateSprites();
 
                    // check for collision
                    checkCollisions();
 
                    // removed dead things
                    cleanupSprites();
                    
                    // checks if the level is over
                    // and if we should move to next screen
                    checkIfLevelOver();
 
				}
        }); // oneFrame
 
        // sets the game world's game loop (Timeline)
        Timeline timeline= new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(oneFrame);
        setGameLoop(timeline);
    }
 
    /**
     * Initialize the game world by update the JavaFX Stage.
     * @param primaryStage
     */
    public abstract void initialize(Stage primaryStage, LevelChanger lC);
 
    /**Kicks off (plays) the Timeline objects containing one key frame
     * that simply runs indefinitely with each frame invoking a method
     * to update sprite objects, check for collisions, and cleanup sprite
     * objects.
     *
     */
    public void beginGameLoop() {
        getGameLoop().play();
    }
 
    /**
     * Updates each game sprite in the game world. This method will
     * loop through each sprite and passing it to the handleUpdate()
     * method. The derived class should override handleUpdate() method.
     *
     */
    protected void updateSprites() {
        for (Object sprite:spriteManager.getAllSprites()){
            handleUpdate((Sprite) sprite);
        }
    }
 
    /** Updates the sprite object's information to position on the game surface.
     * @param sprite - The sprite to update.
     */
    protected void handleUpdate(Sprite sprite) {
    	return;
    }
 
    /**
     * Checks each game sprite in the game world to determine a collision
     * occurred. The method will loop through each sprite and
     * passing it to the handleCollision()
     * method. The derived class should override handleCollision() method.
     *
     */
    protected void checkCollisions() {
        // check other sprite's collisions
        spriteManager.resetCollisionsToCheck();
        // check each sprite against other sprite objects.
        for (Object spriteA:spriteManager.getCollisionsToCheck()){
            for (Object spriteB:spriteManager.getAllSprites()){
                if (handleCollision((Sprite) spriteA, (Sprite) spriteB)) {
                    // The break helps optimize the collisions
                    //  The break statement means one object only hits another
                    // object as opposed to one hitting many objects.
                    // To be more accurate comment out the break statement.
                    break;
                }
            }
        }
    }
 
    /**
     * When two objects collide this method can handle the passed in sprite
     * objects. By default it returns false, meaning the objects do not
     * collide.
     * @param spriteA - called from checkCollision() method to be compared.
     * @param spriteB - called from checkCollision() method to be compared.
     * @return boolean True if the objects collided, otherwise false.
     */
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
    	return false;
    }
 
    /**
     * Sprites to be cleaned up.
     */
    protected void cleanupSprites() {
        spriteManager.cleanupSprites();
    }
 
    /**
     * Returns the frames per second.
     * @return int The frames per second.
     */
    protected int getFramesPerSecond() {
        return framesPerSecond;
    }
 
    /**
     * Returns the game's window title.
     * @return String The game's window title.
     */
    public String getWindowTitle() {
        return windowTitle;
    }
 
    /**
     * The game loop (Timeline) which is used to update, check collisions, and
     * cleanup sprite objects at every interval (fps).
     * @return Timeline An animation running indefinitely representing the game
     * loop.
     */
    protected static Timeline getGameLoop() {
        return gameLoop;
    }
 
    /**
     * The sets the current game loop for this game world.
     * @param gameLoop Timeline object of an animation running indefinitely
     * representing the game loop.
     */
    protected static void setGameLoop(Timeline gameLoop) {
        GameWorld.gameLoop = gameLoop;
    }
 
    /**
     * Returns the sprite manager containing the sprite objects to
     * manipulate in the game.
     * @return SpriteManager The sprite manager.
     */
    protected SpriteManager getSpriteManager() {
        return spriteManager;
    }
 
    /**
     * Returns the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     * @return
     */
    public Scene getGameSurface() {
        return gameSurface;
    }
 
    /**
     * Sets the JavaFX Scene. This is called the game surface to
     * allow the developer to add JavaFX Node objects onto the Scene.
     * @param gameSurface The main game surface (JavaFX Scene).
     */
    protected void setGameSurface(Scene gameSurface) {
        this.gameSurface = gameSurface;
    }
 
    /**
     * All JavaFX nodes which are rendered onto the game surface(Scene) is
     * a JavaFX Group object.
     * @return Group The root containing many child nodes to be displayed into
     * the Scene area.
     */
    public Group getSceneNodes() {
        return sceneNodes;
    }
   
    /**
     * Sets the JavaFX Group that will hold all JavaFX nodes which are rendered
     * onto the game surface(Scene) is a JavaFX Group object.
     * @param sceneNodes The root container having many children nodes
     * to be displayed into the Scene area.
     */
    protected void setSceneNodes(Group sceneNodes) {
        this.sceneNodes = sceneNodes;
    }
    
    /**
     * Sets the background image as a soccer field.
     * @param root The root node of the scene for which the background is a soccer
     * field.
     */
    protected void setBackgroundSoccerField(Group root) {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("images/soccer_field.jpg"));
		ImageView background = new ImageView(image);
		background.setX(0);
		background.setY(0);
		background.setOpacity(0.75);
		root.getChildren().add(background);
	}
    
    protected void checkIfLevelOver() {
    	return;
    }
 
}