import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Drone extends Sprite {

	private final String DRONE_IMAGE_URL = "images/drone.png";
	private final String MISSILE_IMAGE_URL = "images/black_ball.jpg";
	private final int SHOT_FREQUENCY = 100;
	protected final Image missileImage = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE_URL));
	protected Messi messi;
	protected int shotCounter = SHOT_FREQUENCY;
	
	
	public Drone() {
		health = 1;
		node = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(DRONE_IMAGE_URL)));
		vX = 5;
		vY = 1;
		damage= 1;
		vState = VState.INVULNERABLE;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		setX(getX() + vX);
		setY(getY()+vY);
		if(vState == vState.VULNERABLE) {
			decrementShotCounter();
		}
		
		if(getY()>100) {
			vState = vState.VULNERABLE;
		}
		return;
	}
	
	public Missile fire() {
		Missile newMissile = new Missile(missileImage, this, 0, 5);
		return newMissile;
	}
	
	public boolean collide(Sprite other) {
			if(other instanceof Missile) {
				Missile missile = (Missile) other;
				if(missile.owner instanceof Messi) {
					health-= other.getDamage();
				}
			}
			return true;
	}
	
	public int getShotCounter() {
		return shotCounter;
	}
	
	public void resetShotCounter() {
		shotCounter = SHOT_FREQUENCY;
	}
	
	public void decrementShotCounter() {
		shotCounter--;
	}
	
	@Override
	public void incomingAnimation(GameWorld gameWorld) {
		FadeTransition fade = new FadeTransition(Duration.millis(3000), this.getNode());
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
	}
	
	@Override
	public void impactAnimation(GameWorld gameWorld) {
		vX=0;
		vY=0;
		FadeTransition fade = new FadeTransition(Duration.millis(500), this.getNode());
        fade.setFromValue(node.getOpacity());
        fade.setToValue(0);
        fade.play();
        fade.setOnFinished(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				isDead = true;
                gameWorld.getSceneNodes().getChildren().remove(node);
			}
        });
        fade.play();
	}
		
}
