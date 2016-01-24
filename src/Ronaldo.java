import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Ronaldo extends Sprite {

	private final static String RONALDO_IMAGE_URL = "images/ronaldo_image.jpg";
	private final static String MISSILE_IMAGE_URL = "images/black_ball.jpg";
	private final int SHOT_FREQUENCY = 50;
	protected final Image missileImage = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE_URL));
	protected Messi messi;
	protected int shotCounter = SHOT_FREQUENCY;
	
	public Ronaldo(Messi mes) {
		messi = mes;
		health = 10;
		node = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(RONALDO_IMAGE_URL)));
		vX = 4;
		damage= 0;
		vState = VState.VULNERABLE;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(vState == vState.VULNERABLE) {
			setX(getX() + vX);
			setY(getY()+vY);
			decrementShotCounter();
		}
		return;
	}
	
	public Missile fire() {
		Missile newMissile = new Missile(missileImage, this, (messi.getX()-this.getX())/((messi.getY()-this.getY())/10), 10);
		return newMissile;
	}
	
	public boolean collide(Sprite other) {
			if(other instanceof Missile) {
				if(!((Missile)other).owner.equals(this)) {
					health-= other.getDamage();
				}
			}
			else {
				if(vState == vState.VULNERABLE) {
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
}
