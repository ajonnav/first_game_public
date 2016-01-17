import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

class LM10Game {
	private static final String TITLE = "LM10: Ronaldo's Revenge";
	public static final int KEY_INPUT_SPEED = 5;
	
	private Scene myScene;
	private ImageView messiImage;
	private ImageView background;
	
	/**
     * Returns name of the game.
     */
	public String getTitle () {
        return TITLE;
    }
	
	public Scene init(int width, int height){
		Group root = new Group();
		myScene = new Scene(root, width, height, Color.WHITE);
		Image image = new Image(getClass().getClassLoader().getResourceAsStream("messi_image.jpg"));
		messiImage= new ImageView(image);
		messiImage.setX(width / 2 - messiImage.getBoundsInLocal().getWidth() / 2);
		messiImage.setY(height - messiImage.getBoundsInLocal().getHeight()*1.5);
		image = new Image(getClass().getClassLoader().getResourceAsStream("soccer_field.jpg"));
		background = new ImageView(image);
		background.setX(0);
		background.setY(0);
		background.setOpacity(0.75);
		
		root.getChildren().add(background);
        root.getChildren().add(messiImage);
		return myScene;
	}
	
	public void step(double elapsedTime){
		return;
	}
}
