import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

public abstract class Sprite {
 
    /** Animation for the node */
    protected List animations = new ArrayList<>();
 
    /** Current display node */
    protected Node node;
 
    /** velocity vector x direction */
    protected double vX = 0;
 
    /** velocity vector y direction */
    protected double vY = 0;
 
    /** dead? */
    protected boolean isDead = false;
    
    protected int health;
    
    protected int damage;
    
    public VState vState;
 
    /**
     * Updates this sprite object's velocity, or animations.
     */
    public abstract void update();
 
    /**
     * Did this sprite collide into the other sprite?
     *
     * @param other - The other sprite.
     * @return
     */
    public boolean collide(Sprite other) {
        return false;
    }
    
    public Node getNode() {
		return node;
	}
    
    public double getX() {
		return getNode().getTranslateX();
	}
	
	public double getY() {
		return getNode().getTranslateY();
	}
	
	public void setX(double X){
		getNode().setTranslateX(X);
	}
	
	public void setY(double Y){
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
	
	public void impactAnimation(GameWorld gameWorld) {
		return;
	}
	
	public void incomingAnimation(GameWorld gameWorld) {
		
	}
	
	public void removeAtEndOfLevelAnimation(GameWorld gameWorld) {
		vX=0;
		vY=0;
		FadeTransition fade = new FadeTransition(Duration.millis(3000), this.getNode());
        fade.setFromValue(node.getOpacity());
        fade.setToValue(0);
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