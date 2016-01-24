import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;


public class Messi extends Sprite{
	
	private final static String MESSI_IMAGE_URL = "images/messi_image.jpg";
	private final static String MISSILE_IMAGE_URL = "images/soccer_ball.png";
	protected final Image missileImage = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE_URL));
	private final static double CHANGE_ACCEL = 2;
	
	public Messi() {
		node = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(MESSI_IMAGE_URL)));
		health=2;
		damage = 0;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		setX(getX() + vX);
		setY(getY()+vY);
		return;
	}
	
	@Override
	public boolean collide(Sprite other) {
			if(other instanceof Missile) {
				if(!((Missile)other).owner.equals(this)) {
					health-= other.getDamage();
				}
			}
			else {
				health-= other.getDamage();
			}
			return true;
	}
	
	public void accelerateLeft() {
		if(vX>-6)
			vX-=CHANGE_ACCEL;
	}
	
	public void accelerateRight() {
		if(vX<6)
			vX+=CHANGE_ACCEL;
	}
	
	public Missile fire() {
		Missile newMissile = new Missile(missileImage, this);
		return newMissile;
	}
}
