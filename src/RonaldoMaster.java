// This entire file is part of my masterpiece.
// Anirudh Jonnavithula

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class RonaldoMaster extends SpriteMaster {

	private final String RONALDO_IMAGE_URL = "images/ronaldo_image.jpg";
	private final String MISSILE_IMAGE_URL = "images/black_ball.jpg";
	private final int SHOT_FREQUENCY = 50;
	private final int MAX_HEALTH = 10;
	private final int VX = 4;
	private final int VY = 0;
	private final int DAMAGE = 0;
	private final int MISSILE_VY = 10;
	private final Image missileImage = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE_URL));
	private MessiMaster messi;
	private int shotCounter = SHOT_FREQUENCY;
	
	public RonaldoMaster(MessiMaster messi) {
		this.messi = messi;
		setHealth(MAX_HEALTH);
		setNode(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(RONALDO_IMAGE_URL))));
		setvX(VX);
		setvY(VY);
		setDamage(DAMAGE);
		setvState(VState.VULNERABLE);
		setSide(Side.BAD);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		setX(getX() + getvX());
		setY(getY() + getvY());
		decrementShotCounter();
		return;
	}
	
	public MissileMaster fire() {
		MissileMaster newMissile = new MissileMaster(missileImage, this, getMissileVX(), MISSILE_VY);
		return newMissile;
	}
	
	public boolean collide(SpriteMaster other) {
			if(other.getSide() == Side.GOOD) {
				setHealth(getHealth() - other.getDamage());
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
	
	private double getMissileVX() {
		return (messi.getX()-this.getX())/((messi.getY()-this.getY())/MISSILE_VY);
	}
}
