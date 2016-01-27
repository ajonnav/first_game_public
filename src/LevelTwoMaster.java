// This entire file is part of my masterpiece.
// Anirudh Jonnavithula
// This class is the class for Level Two.
//It is well designed because it does not
//repeat code by making use of a superclass. 

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LevelTwoMaster extends LevelMaster{
	
	private RonaldoMaster ronaldo = new RonaldoMaster(messi());
	private final String WINNING_MESSAGE = "YOU BEAT\nRONALDO";
	private final String LOSING_MESSAGE = "YOU LOST\nRESTART GAME";
	private final int RONALDO_STARTING_Y = 10; 
	private final int OUT_OF_BOUNDS = -30;
	private final int FONT_SIZE = 70;
	
	public LevelTwoMaster(int fps, String title, int width, int height) {
		super(fps, title, width, height);
	}

	@Override
	public void initialize(Stage primaryStage, LevelChanger lC) {
		
		setLevelChanger(lC);
		setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), getWidth(), getHeight()));
        setBackgroundSoccerField(getSceneNodes());
        
		setMessiInitialPosition();
		
		setRonaldoInitialPosition();
		
        primaryStage.setScene(getGameSurface());
        setupInput(primaryStage);
        //setupNextLevel(primaryStage);
	}

	private void setRonaldoInitialPosition() {
		ronaldo.setX(getWidth()/2 - ronaldo.getWidth()/2);
		ronaldo.setY(RONALDO_STARTING_Y);
		getSceneNodes().getChildren().add(ronaldo.getNode());
		getSpriteManager().addSprites(ronaldo);
	}
	
	@Override
	protected void handleUpdate(SpriteMaster sprite) {
		if(sprite instanceof MessiMaster) {
			handleUpdateMessi((MessiMaster)sprite);
		}
		
		if(sprite instanceof RonaldoMaster) {
			handleUpdateRonaldo((RonaldoMaster)sprite);
		}
		
		if(sprite instanceof MissileMaster) {
			handleUpdateMissile((MissileMaster)sprite);
		}
	}

	private void handleUpdateMissile(MissileMaster missile) {
		if(missile.getY()<OUT_OF_BOUNDS || missile.getY() > getHeight()) {
			getSpriteManager().addSpritesToBeRemoved(missile);
		}
		else {
			missile.update();
		}
	}

	private void handleUpdateRonaldo(RonaldoMaster ronaldo) {
		if((ronaldo).getShotCounter() == 0) {
			(ronaldo).resetShotCounter();
			MissileMaster newMissile = ronaldo.fire();
			newMissile.setX(ronaldo.getX());
			newMissile.setY(ronaldo.getY());
			getSceneNodes().getChildren().add(newMissile.getNode());
			getSpriteManager().addSprites(newMissile);
		}
		
		if(ronaldo.getX()<=bounds() || ronaldo.getX()>=getWidth() - ronaldo.getWidth() - bounds()) {
			ronaldo.setvX(-ronaldo.getvX()) ;
		}
		ronaldo.update();
	}

	private void handleUpdateMessi(MessiMaster messi) {
		if(messi().getX()<=bounds()) {
			if(messi().getvX()<0)
				messi().setvX(0);
		}
		else if(messi().getX()>=getWidth() - messi().getWidth() - bounds()){
			if(messi().getvX()>0)
				messi().setvX(0);
		}
		
		messi().update();
	}

	private void stopLevel(int status) {
		getGameLoop().stop();		
		Text newText;
		if(status == win()) {
			 newText = new Text(WINNING_MESSAGE);
		}
		else {
			 newText = new Text(LOSING_MESSAGE);
		}
		
		for(SpriteMaster sprite: getSpriteManager().getAllSprites()) {
			sprite.onRemoveAnimation(this);
		}

		newText.setFont(Font.font("Times", FONT_SIZE));
		newText.setTranslateX(getWidth()/2 - newText.getBoundsInLocal().getWidth()/2);
		newText.setTranslateY(getHeight()/2 - newText.getBoundsInLocal().getHeight()/2);
		newText.setFill(Color.RED);
		getSceneNodes().getChildren().add(newText);
	}
	
	@Override
	protected boolean handleCollision(SpriteMaster spriteA, SpriteMaster spriteB) {
		if(spriteA.getNode().getBoundsInParent().intersects(spriteB.getNode().getBoundsInParent())) {
    		spriteA.collide(spriteB);
    		spriteB.collide(spriteA);
    		
    		if(spriteA.getSide() == spriteB.getSide()) {
    			return true;
    		}
    		if(spriteA instanceof MissileMaster && spriteB instanceof MissileMaster) {
    			return true;
    		}
    		if(spriteA instanceof MissileMaster) {
    			getSpriteManager().addSpritesToBeRemoved(spriteA);
    			spriteA.onRemoveAnimation(this);
    		}
    		if(spriteB instanceof MissileMaster) {
				getSpriteManager().addSpritesToBeRemoved(spriteB);
				spriteB.onRemoveAnimation(this);
    		}
    		return true;
    	}
    	return false;
	}

	@Override
	protected void checkIfLevelOver() {
		// TODO Auto-generated method stub
		if(ronaldo.getHealth() == 0) {
			stopLevel(win());
		}
		if(messi().getHealth() == 0) {
			stopLevel(lose());
		}
	}
}
