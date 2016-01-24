import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class Missile extends Sprite {
	
	protected Sprite owner;
	
	public Missile(Image image, Sprite own) {
		
		owner=own;
		if(owner instanceof Messi) {
			vY = -10;
		}
		else {
			vY = 10;
		}
		damage = 1;
		node = new ImageView(image);
	}
	
public Missile(Image image, Sprite own, double vx, double vy) {
		
		owner=own;
		if(owner instanceof Messi) {
			vY = -10;
		}
		else {
			vY = 10;
		}
		vX= vx;
		damage = 1;
		node = new ImageView(image);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		setY(getY() + vY);
		setX(getX() + vX);
		return;
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
