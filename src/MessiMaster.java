//This entire file is part of my masterpiece
//Anirudh Jonnavithula
//This class represents the player character, Messi.
//It is well designed because it makes use of a super class.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MessiMaster extends SpriteMaster{
	
	private final static String MESSI_IMAGE_URL = "images/messi_image.jpg";
	private final static String MISSILE_IMAGE_URL = "images/soccer_ball.png";
	protected final Image missileImage = new Image(getClass().getClassLoader().getResourceAsStream(MISSILE_IMAGE_URL));
	private final static double CHANGE_ACCEL = 2;
	private final int MAX_HEALTH = 2;
	private final int DAMAGE = 0;
	private final int MISSILE_VY = -10;
	private final int MISSILE_VX = 0;
	private final int VY = 0;
	private final int MAX_ACC = 6;
	
	public MessiMaster() {
		setNode(new ImageView(new Image(getClass().getClassLoader().getResourceAsStream(MESSI_IMAGE_URL))));
		setHealth(MAX_HEALTH);
		setDamage(DAMAGE);
		setSide(Side.GOOD);
		setvY(VY);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		setX(getX() + getvX());
		setY(getY()+getvY());
		return;
	}
	
	@Override
	public boolean collide(SpriteMaster other) {
		if(other.getSide() == Side.BAD) {
			setHealth(getHealth() - other.getDamage());
		}
		return true;
	}
	
	public void accelerateLeft() {
		if(getvX()>-MAX_ACC)
			setvX(getvX() - CHANGE_ACCEL);
	}
	
	public void accelerateRight() {
		if(getvX()<MAX_ACC)
			setvX(getvX() + CHANGE_ACCEL);
	}
	
	public MissileMaster fire() {
		MissileMaster newMissile = new MissileMaster(missileImage, this, MISSILE_VX, MISSILE_VY);
		return newMissile;
	}
}
