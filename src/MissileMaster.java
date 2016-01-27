// This entire file is part of my masterpiece.
// Anirudh Jonnavithula
//This class represents missiles shot by .
//It is well designed because it different 
//classes (MessiMaster and RonaldoMaster)
//make use of it but the code is the same.
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class MissileMaster extends SpriteMaster {
	
	private final int DAMAGE=1;
	private SpriteMaster owner;
	
	public MissileMaster(Image image, SpriteMaster owner, double vx, double vy) {
		setOwner(owner);
		setvY(vy);
		setvX(vx);
		setDamage(DAMAGE);
		setNode(new ImageView(image));
		setSide(owner.getSide());
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		setY(getY() + getvY());
		setX(getX() + getvX());
		return;
	}
	
	public SpriteMaster getOwner() {
		return owner;
	}
	
	@Override
	public boolean collide(SpriteMaster other) {
		return true;
	}
	
	private void setOwner(SpriteMaster owner) {
		this.owner = owner;
	}
}
