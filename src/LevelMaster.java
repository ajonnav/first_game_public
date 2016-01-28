

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public abstract class LevelMaster extends GameWorldMaster {

	private MessiMaster messi = new MessiMaster();
	
	public LevelMaster(int fps, String title, int wide, int high) {
		super(fps, title, wide, high);
		// TODO Auto-generated constructor stub
	}

	protected void setupInput(Stage s) {
		EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				// TODO Auto-generated method stub
				KeyCode eventCode = keyEvent.getCode();
				if(eventCode == KeyCode.LEFT && messi.getX()>bounds()) {
					messi.accelerateLeft();
				}
				if (eventCode ==KeyCode.RIGHT && messi.getX()<(getWidth()-messi.getWidth()-bounds())) {
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
					MissileMaster newMissile = messi.fire();
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
	
	protected void setMessiInitialPosition() {
		messi().setX(getWidth()/2 - messi().getWidth()/2);
		messi().setY(getHeight() - messi().getHeight()*2);
		getSceneNodes().getChildren().add(messi().getNode());
		getSpriteManager().addSprites(messi());
	}
	
	public MessiMaster messi() {
		return messi;
	}
}
