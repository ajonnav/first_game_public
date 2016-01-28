import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public abstract class SpriteMaster {

    /** Current display node */
    private Node node;
 
    /** velocity vector x direction */
    private double vX = 0;
 
    /** velocity vector y direction */
    private double vY = 0;
    
    private int health;
    
    private int damage;
    
    private VState vState;
    
    private Side side;
    
    private final int ANIMATION_TIME = 500;
 
    /**
     * Updates this sprite object's velocity, or other things.
     */
    public abstract void update();
 
    /**
     * Update the sprite when it collides with another sprite
     *
     * @param other - The other sprite.
     * @return
     */
    public boolean collide(SpriteMaster other) {
        return false;
    }
    
    public void onRemoveAnimation(GameWorldMaster gameWorld) {
		setvX(0);
		setvY(0);
		FadeTransition fade = new FadeTransition(Duration.millis(ANIMATION_TIME), this.getNode());
        fade.setFromValue(getNode().getOpacity());
        fade.setToValue(0);
        fade.setOnFinished(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
                gameWorld.getSceneNodes().getChildren().remove(getNode());
			}
        });
        fade.play();
	}
    
    public Node getNode() {
		return node;
	}
    
    protected void setNode(Node node) {
		this.node = node;
	}
    
    public double getX() {
		return getNode().getTranslateX();
	}
	
    protected void setX(double X){
		getNode().setTranslateX(X);
	}
	
	public double getY() {
		return getNode().getTranslateY();
	}
	
	protected void setY(double Y){
		getNode().setTranslateY(Y);
	}
	
	public double getWidth() {
		return getNode().getBoundsInLocal().getWidth();
	}
	
	public double getHeight() {
		return getNode().getBoundsInLocal().getWidth();
	}
	
	public int getDamage() {
		return damage;
	}
	
	protected void setDamage(int damage) {
		this.damage = damage;
	}

	public double getvX() {
		return vX;
	}

	protected void setvX(double vX) {
		this.vX = vX;
	}

	public double getvY() {
		return vY;
	}

	protected void setvY(double vY) {
		this.vY = vY;
	}

	public int getHealth() {
		return health;
	}

	protected void setHealth(int health) {
		this.health = health;
	}

	public VState getvState() {
		return vState;
	}

	public void setvState(VState vState) {
		this.vState = vState;
	}

	public Side getSide() {
		return side;
	}

	protected void setSide(Side side) {
		this.side = side;
	}
}