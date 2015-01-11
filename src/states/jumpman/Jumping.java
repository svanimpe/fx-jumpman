package states.jumpman;

import objects.Jumpman;
import util.Sprite;
import util.Vector2D;

public class Jumping implements JumpmanState {

    public enum Type { DECEL_ON_LANDING, GO_ON_LANDING, DUCK_ON_LANDING, BRAKE_ON_LANDING };
    
    private final Jumpman jm;
    private double timeElapsed = 0;
    private Type type;
    
    public Jumping(Jumpman jm, Type type) {
        this.jm = jm;
        this.type = type;
        jm.setVelocity(jm.getVelocity().add(Jumpman.JUMP));
        jm.setAcceleration(Vector2D.ZERO);
        jm.setSprite(jm.isSmall() ? Sprite.JM_JUMP_SMALL : Sprite.JM_JUMP);
    }
    
    @Override
    public void rightPressed() {
        if (jm.isFacingRight() && type == Type.DECEL_ON_LANDING) {
            type = Type.GO_ON_LANDING;
        } else if (jm.isFacingLeft() && type == Type.DECEL_ON_LANDING) {
            type = Type.BRAKE_ON_LANDING;
        }
    }

    @Override
    public void rightReleased() {
        if (jm.isFacingRight() && type == Type.GO_ON_LANDING) {
            type = Type.DECEL_ON_LANDING;
        } else if (jm.isFacingLeft() && type == Type.BRAKE_ON_LANDING) {
            type = Type.DECEL_ON_LANDING;
        }
    }

    @Override
    public void leftPressed() {
        if (jm.isFacingLeft() && type == Type.DECEL_ON_LANDING) {
            type = Type.GO_ON_LANDING;
        } else if (jm.isFacingRight() && type == Type.DECEL_ON_LANDING) {
            type = Type.BRAKE_ON_LANDING;
        }
    }

    @Override
    public void leftReleased() {
        if (jm.isFacingLeft() && type == Type.GO_ON_LANDING) {
            type = Type.DECEL_ON_LANDING;
        } else if (jm.isFacingRight() && type == Type.BRAKE_ON_LANDING) {
            type = Type.DECEL_ON_LANDING;
        }
    }

    @Override
    public void downPressed() {
        if (type == Type.DECEL_ON_LANDING) {
            type = Type.DUCK_ON_LANDING;
        }
    }
    
    @Override
    public void downReleased() {
        if (type == Type.DUCK_ON_LANDING) {
            type = Type.DECEL_ON_LANDING;
        }
    }
    
    @Override
    public void runPressed() {
        jm.setReadyToRun(true);
    }

    @Override
    public void runReleased() {
        jm.setReadyToRun(false);
    }

    @Override
    public void jumpPressed() {
    }

    private boolean released = false;
    
    @Override
    public void jumpReleased() {
        if (!released) {
            jm.setAcceleration(Jumpman.GRAVITY);
            released = true;
        }
    }

    @Override
    public void checkEvents(double t) {
        timeElapsed += t;
        if (!released && timeElapsed >= 0.3) {
            jumpReleased();
        }
        
        if (jm.getPosition().getY() <= 0) {
            jm.setPosition(new Vector2D(jm.getPosition().getX(), 0));
            jm.setVelocity(new Vector2D(jm.getVelocity().getX(), 0));
            jm.setAcceleration(Vector2D.ZERO);
            
            if (type == Type.DECEL_ON_LANDING) {
                jm.setState(new Decelerating(jm, Decelerating.Type.TO_STAND));
            } else if (type == Type.GO_ON_LANDING && !jm.isReadyToRun()) {
                jm.setState(new Decelerating(jm, Decelerating.Type.TO_WALK));
            } else if (type == Type.GO_ON_LANDING && jm.isReadyToRun()) {
                jm.setState(new Running(jm));
            } else if (type == Type.DUCK_ON_LANDING) {
                jm.setState(new Ducking(jm));
            } else if (type == Type.BRAKE_ON_LANDING) {
                jm.setState(new Braking(jm));
            }
        }
    }   
}
