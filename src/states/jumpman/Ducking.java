package states.jumpman;

import objects.Jumpman;
import util.Sprite;
import util.Vector2D;

public class Ducking implements JumpmanState {

    private final Jumpman jm;
    
    public Ducking(Jumpman jm) {
        this.jm = jm;
        jm.setAcceleration(jm.isFacingRight() ? Jumpman.BRAKE : Jumpman.BRAKE.flip());
        jm.setSprite(jm.isSmall() ? Sprite.JM_STAND_SMALL : Sprite.JM_DUCK);
    }
    
    @Override
    public void rightPressed() {
    }

    @Override
    public void rightReleased() {
    }

    @Override
    public void leftPressed() {
    }

    @Override
    public void leftReleased() {
    }

    @Override
    public void downPressed() {
        
    }
    
    @Override
    public void downReleased() {
        if (jm.getVelocity() == Vector2D.ZERO) {
            jm.setState(new Standing(jm));
        } else {
            jm.setState(new Decelerating(jm, Decelerating.Type.TO_STAND));
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
        jm.setState(new Jumping(jm, Jumping.Type.DUCK_ON_LANDING));
    }

    @Override
    public void jumpReleased() {
    }

    @Override
    public void checkEvents(double t) {
        if (jm.isFacingRight() && jm.getVelocity().getX() <= 0) {
            jm.setVelocity(Vector2D.ZERO);
            jm.setAcceleration(Vector2D.ZERO);
        } else if (jm.isFacingLeft() && jm.getVelocity().getX() >= 0) {
            jm.setVelocity(Vector2D.ZERO);
            jm.setAcceleration(Vector2D.ZERO);
        }
    }   
}
