import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LevelOne extends GameWorld{
	private Messi messi = new Messi();
	private final int BOUNDS =5;
	private int WIDTH;
	private int HEIGHT;
	private int FRAMES_PER_SECOND;
	private int numDrones=50;
	
	public LevelOne(int fps, String title, int width, int height) {
		super(fps, title);
		WIDTH =width;
		HEIGHT= height;
		FRAMES_PER_SECOND =fps;
	}

	@Override
	public void initialize(Stage primaryStage, LevelChanger lC) {
		// TODO Auto-generated method stub
		levelChanger = lC;
		setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));
        setBackgroundSoccerField(getSceneNodes());
        
        generateDrones(50);
        Drone drone = new Drone();
        drone.setX(20);
        drone.setY(100);
        getSceneNodes().getChildren().add(drone.getNode());
		getSpriteManager().addSprites(drone);
		
		drone = new Drone();
        
        
        primaryStage.setScene(getGameSurface());
		setMessiInitialPosition();
        setupInput(primaryStage);
	}

	public void generateDrones(int num) {
		for(int counter =0; counter<num; counter++) {
			Drone drone = new Drone();
			drone.setX(ThreadLocalRandom.current().nextInt(BOUNDS, WIDTH + 1 -BOUNDS*2));
	        drone.setY(-ThreadLocalRandom.current().nextInt(0, 5000 + 1));
	        getSceneNodes().getChildren().add(drone.getNode());
			getSpriteManager().addSprites(drone);
		}
		return;
	}
	
	@Override
	protected void checkIfLevelOver() {
		// TODO Auto-generated method stub
		if(messi.health == 0) {
			stopLevel(LOSE);	
			levelChanger.showLosingScreen();
			//winningScreen();
		}
		if(numDrones == 0) {
			stopLevel(WIN);
		}
	}
	
	private void stopLevel(int status) {
		getGameLoop().stop();
		
		Text newText;
		if(status == WIN) {
			 newText = new Text("YOU BEAT\nTHE LEVEL");
		}
		else {
			 newText = new Text("YOU LOST\nRESTART GAME");
		}
		
		for(Sprite sprite: getSpriteManager().getAllSprites()) {
			sprite.removeAtEndOfLevelAnimation(this);
		}

		newText.setFont(Font.font("Times", 60));
		newText.setTranslateX(WIDTH/2 - newText.getBoundsInLocal().getWidth()/2);
		newText.setTranslateY(HEIGHT/2 - newText.getBoundsInLocal().getHeight()/2);
		newText.setFill(Color.RED);
		getSceneNodes().getChildren().add(newText);
		
		//levelChanger.showSplashBeforeLevelTwo();

	}
	
	@Override
	protected void handleUpdate(Sprite sprite) {
		if(sprite instanceof Messi) {
			Messi mes = (Messi) sprite;
			if(sprite.getX()<=BOUNDS) {
				if(sprite.vX<0);
					//sprite.vX = 0;
			}
			else if(sprite.getX()>=WIDTH - sprite.getWidth() - BOUNDS){
				if(sprite.vX>0);
					//sprite.vX= 0;
			}
			
			sprite.update();
		}
		
		if(sprite instanceof Ronaldo) {
			Ronaldo ronaldo = (Ronaldo)sprite;
			if((ronaldo).getShotCounter() == 0) {
		
				(ronaldo).resetShotCounter();
				Missile newMissile = ronaldo.fire();
				newMissile.setX(ronaldo.getX());
				newMissile.setY(ronaldo.getY());
				getSceneNodes().getChildren().add(newMissile.getNode());
				getSpriteManager().addSprites(newMissile);
			}
			
			if(ronaldo.getX()<=BOUNDS || ronaldo.getX()>=WIDTH - sprite.getWidth() - BOUNDS) {
				ronaldo.vX = -ronaldo.vX ;
			}
			sprite.update();
		}
		
		if(sprite instanceof Drone) {
			Drone drone = (Drone)sprite;
			if(sprite.getY() > HEIGHT) {
				getSpriteManager().addSpritesToBeRemoved(sprite);
				numDrones --;
			}
			else {
				if(drone.getShotCounter() == 0) {
			
					drone.resetShotCounter();
					Missile newMissile = drone.fire();
					newMissile.setX(drone.getX());
					newMissile.setY(drone.getY());
					getSceneNodes().getChildren().add(newMissile.getNode());
					getSpriteManager().addSprites(newMissile);
				}
				
				if(drone.getX()<=BOUNDS) {
					drone.vX = 5;
				}
				if(drone.getX()>=WIDTH - sprite.getWidth() - BOUNDS) {
					drone.vX = -5;
				}
				
				sprite.update();
			}
		}
		
		if(sprite instanceof Missile) {
			sprite = (Missile) sprite;
			if(sprite.getY()<-30 || sprite.getY() > HEIGHT) {
				getSpriteManager().addSpritesToBeRemoved(sprite);
			}
			else {
				sprite.update();
			}
		}
	}
	

	@Override
	protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
		if(spriteA.getNode().getBoundsInParent().intersects(spriteB.getNode().getBoundsInParent())) {
    		spriteA.collide(spriteB);
    		spriteB.collide(spriteA);
    		if(spriteA instanceof Missile) {
    			Missile missile = (Missile) spriteA;
    			if(!(spriteB instanceof Missile) && !(missile.owner.equals(spriteB))) {
    				getSpriteManager().addSpritesToBeRemoved(spriteA);
    				missile.impactAnimation(this);
    				if(spriteB instanceof Drone && missile.owner.equals(messi)) {
        				getSpriteManager().addSpritesToBeRemoved(spriteB);
        				spriteB.impactAnimation(this);
        				numDrones --;
        			}
    			}	
    		}
    		if(spriteB instanceof Missile) {
    			Missile missile = (Missile) spriteB;
    			if(!(spriteA instanceof Missile) && !(missile.owner.equals(spriteA))) {
    				getSpriteManager().addSpritesToBeRemoved(spriteB);
    				missile.impactAnimation(this);
    				if(spriteA instanceof Drone && missile.owner.equals(messi)) {
        				getSpriteManager().addSpritesToBeRemoved(spriteA);
        				spriteA.impactAnimation(this);
        				numDrones --;
        			}
    			}
    		}
    		if(spriteA instanceof Messi && spriteB instanceof Drone) {
    			getSpriteManager().addSpritesToBeRemoved(spriteB);
				spriteB.impactAnimation(this);
				numDrones --;
    		}
    		
    		if(spriteA instanceof Drone && spriteB instanceof Messi) {
    			getSpriteManager().addSpritesToBeRemoved(spriteA);
				spriteA.impactAnimation(this);
				numDrones--;
    		}
    		
    		return true;
    	}
    	return false;
	}
	
	private void setupInput(Stage s) {
		EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				// TODO Auto-generated method stub
				KeyCode eventCode = keyEvent.getCode();
				if(eventCode == KeyCode.LEFT && messi.getX()>10) {
					messi.accelerateLeft();
				}
				if (eventCode == KeyCode.RIGHT ) {
					messi.accelerateRight();
				}
			}			
		};
		
		EventHandler<KeyEvent> keyReleaseHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				// TODO Auto-generated method stub
				KeyCode eventCode = keyEvent.getCode();
				if(eventCode ==KeyCode.SPACE) {
					Missile newMissile = messi.fire();
					newMissile.setX(messi.getX());
					newMissile.setY(messi.getY());
					getSceneNodes().getChildren().add(newMissile.getNode());
					getSpriteManager().addSprites(newMissile);
				}
			}			
		};
		
		s.getScene().setOnKeyPressed(keyPressHandler);
		s.getScene().setOnKeyReleased(keyReleaseHandler);
	}
	
	private void setMessiInitialPosition() {
		messi.setX(WIDTH/2 - messi.getWidth()/2);
		messi.setY(HEIGHT - messi.getHeight()*2);
		getSceneNodes().getChildren().add(messi.getNode());
		getSpriteManager().addSprites(messi);
	}
}
