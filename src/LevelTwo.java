import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LevelTwo extends GameWorld{
	
	private Messi messi = new Messi();
	private Ronaldo ronaldo = new Ronaldo(messi);
	private int FRAMES_PER_SECOND;
	private final int BOUNDS =5;
	private int WIDTH;
	private int HEIGHT;
	
	public LevelTwo(int fps, String title, int width, int height) {
		super(fps, title);
		FRAMES_PER_SECOND =fps;
		WIDTH =width;
		HEIGHT= height;
	}

	@Override
	public void initialize(Stage primaryStage, LevelChanger lC) {
		
		levelChanger = lC;
		
		setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), WIDTH, HEIGHT));
        setBackgroundSoccerField(getSceneNodes());
        
		setMessiInitialPosition();
		
		setRonaldoInitialPosition();
		
        primaryStage.setScene(getGameSurface());
        setupInput(primaryStage);
        //setupNextLevel(primaryStage);
	}

	private void setRonaldoInitialPosition() {
		ronaldo.setX(WIDTH/2 - messi.getWidth()/2);
		ronaldo.setY(10);
		getSceneNodes().getChildren().add(ronaldo.getNode());
		getSpriteManager().addSprites(ronaldo);
	}

	private void setMessiInitialPosition() {
		messi.setX(WIDTH/2 - messi.getWidth()/2);
		messi.setY(HEIGHT - messi.getHeight()*2);
		getSceneNodes().getChildren().add(messi.getNode());
		getSpriteManager().addSprites(messi);
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
				if (eventCode ==KeyCode.RIGHT ) {
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
	
	@Override
	protected void handleUpdate(Sprite sprite) {
		if(sprite instanceof Messi) {
			Messi mes = (Messi) sprite;
			if(sprite.getX()<=BOUNDS) {
				if(sprite.vX<0)
					sprite.vX = 0;
			}
			else if(sprite.getX()>=WIDTH - sprite.getWidth() - BOUNDS){
				if(sprite.vX>0)
					sprite.vX= 0;
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

	private void stopLevel(int status) {
		getGameLoop().stop();		
		Text newText;
		if(status == WIN) {
			 newText = new Text("YOU BEAT\nRONALDO");
		}
		else {
			 newText = new Text("YOU LOST\nRESTART GAME");
		}
		
		for(Sprite sprite: getSpriteManager().getAllSprites()) {
			sprite.removeAtEndOfLevelAnimation(this);
		}

		newText.setFont(Font.font("Times", 70));
		newText.setTranslateX(WIDTH/2 - newText.getBoundsInLocal().getWidth()/2);
		newText.setTranslateY(HEIGHT/2 - newText.getBoundsInLocal().getHeight()/2);
		newText.setFill(Color.RED);
		getSceneNodes().getChildren().add(newText);		
		
		//levelChanger.showMenuScreen();
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
    			}	
    		}
    		if(spriteB instanceof Missile) {
    			Missile missile = (Missile) spriteB;
    			if(!(spriteA instanceof Missile) && !(missile.owner.equals(spriteA))) {
    				getSpriteManager().addSpritesToBeRemoved(spriteA);
    				
    			}
    		}
    		
    		return true;
    	}
    	return false;
	}

	@Override
	protected void checkIfLevelOver() {
		// TODO Auto-generated method stub
		if(ronaldo.health == 0) {
			stopLevel(WIN);			
			//winningScreen();
		}
		if(messi.health == 0) {
			stopLevel(LOSE);			
			//winningScreen();
		}
	}	
	
	public void setupNextLevel(Stage primaryStage) {
		getGameLoop().setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				// TODO Auto-generated method stub
			}			
		}
		);
	}
}
