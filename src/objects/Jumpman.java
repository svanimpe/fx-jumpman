package objects;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import nodebinding.BottomCenterBinding;
import states.jumpman.Dead;
import states.jumpman.JumpmanState;
import states.jumpman.Standing;
import util.Sprite;
import util.Vector2D;

public class Jumpman extends GameObject implements EventHandler<KeyEvent> {

    public static final Vector2D WALK_MAX = new Vector2D(200, 0);
    public static final Vector2D RUN_MAX = new Vector2D(450, 0);
    public static final Vector2D ACCEL = new Vector2D(300, 0);
    public static final Vector2D DECEL = new Vector2D(-600, 0);
    public static final Vector2D BRAKE = new Vector2D(-1200, 0);
    public static final Vector2D JUMP = new Vector2D(0, 300);
    public static final Vector2D GRAVITY = new Vector2D(0, -1200);
    
    public Jumpman() {
        super(Sprite.JM_STAND, new BottomCenterBinding());
        state = new Standing(this);
    }
  
    private boolean small = false;
    private boolean invincible = false;
    
    public boolean isSmall() {
        return small;
    }
    
    public void setSmall() {
        if (!small) {
            small = true;
            setSprite(Sprite.valueOf(getSprite().toString() + "_SMALL"));
        }
    }
    
    public void setLarge() {
        if (small && !(state instanceof Dead)) {
            small = false;
            String name = getSprite().toString();
            setSprite(Sprite.valueOf(name.substring(0, name.indexOf("_SMALL"))));
        }
    }
    
    public void takeHit() {
        if (!invincible) {
            if (!small) {
                setSmall();
                invincible = true;
                getNode().setOpacity(0.5);
                TimelineBuilder.create()
                        .keyFrames(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                invincible = false;
                                getNode().setOpacity(1);
                            }
                        }))
                        .build()
                        .play();
            } else {
                state = new Dead(this);
            }
        }
    }
    
    private boolean facingRight = true;
    
    public boolean isFacingRight() {
        return facingRight;
    }
    
    public boolean isFacingLeft() {
        return !facingRight;
    }
    
    public void faceRight() {
        if (!facingRight) {
            facingRight = true;
            clearTransforms();
        }
    }
    
    public void faceLeft() {
        if (facingRight) {
            facingRight = false;
            flipHorizontal();
        }
    }
    
    private boolean readyToRun = false;
    
    public boolean isReadyToRun() {
        return readyToRun;
    }
    
    public void setReadyToRun(boolean readyToRun) {
        this.readyToRun = readyToRun;
    }
    
    private JumpmanState state;

    public JumpmanState getState() {
        return state;
    }

    public void setState(JumpmanState state) {
        this.state = state;
    }
    
    public void rotateMovementSprites() {
        if (small) {
            if (getSprite() == Sprite.JM_STEP1_SMALL) {
                setSprite(Sprite.JM_STEP2_SMALL);
            } else if (getSprite() == Sprite.JM_STEP2_SMALL) {
                setSprite(Sprite.JM_STAND_SMALL);
            } else {
                setSprite(Sprite.JM_STEP1_SMALL);
            }
        } else {
            if (getSprite() == Sprite.JM_STEP1) {
                setSprite(Sprite.JM_STEP2);
            } else if (getSprite() == Sprite.JM_STEP2) {
                setSprite(Sprite.JM_STAND);
            } else {
                setSprite(Sprite.JM_STEP1);
            }
        }
    }
    
    @Override
    public void update(double t) {
        super.update(t);      
        state.checkEvents(t);
    }
    
    @Override
    public void handle(KeyEvent key) {
        if (key.getCode() == KeyCode.RIGHT && key.getEventType() == KeyEvent.KEY_PRESSED) {
            state.rightPressed();
        } else if (key.getCode() == KeyCode.RIGHT && key.getEventType() == KeyEvent.KEY_RELEASED) {
            state.rightReleased();
        } else if (key.getCode() == KeyCode.LEFT && key.getEventType() == KeyEvent.KEY_PRESSED) {
            state.leftPressed();
        }  else if (key.getCode() == KeyCode.LEFT && key.getEventType() == KeyEvent.KEY_RELEASED) {
            state.leftReleased();
        } else if (key.getCode() == KeyCode.DOWN && key.getEventType() == KeyEvent.KEY_PRESSED) {
            state.downPressed();
        } else if (key.getCode() == KeyCode.DOWN && key.getEventType() == KeyEvent.KEY_RELEASED) {
            state.downReleased();
        } else if (key.getCode() == KeyCode.F && key.getEventType() == KeyEvent.KEY_PRESSED) {
            state.runPressed();
        } else if (key.getCode() == KeyCode.F && key.getEventType() == KeyEvent.KEY_RELEASED) {
            state.runReleased();
        } else if (key.getCode() == KeyCode.SPACE && key.getEventType() == KeyEvent.KEY_PRESSED) {
            state.jumpPressed();
        } else if (key.getCode() == KeyCode.SPACE && key.getEventType() == KeyEvent.KEY_RELEASED) {
            state.jumpReleased();
        }
    }
}
