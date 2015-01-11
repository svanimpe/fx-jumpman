package states.jumpman;

import objects.Jumpman;
import util.Sprite;

public class Braking implements JumpmanState {

    private final Jumpman jm;
    
    public Braking(Jumpman jm) {
        this.jm = jm;
        jm.setAcceleration(jm.isFacingRight() ? Jumpman.BRAKE : Jumpman.BRAKE.flip());
        jm.setSprite(jm.isSmall() ? Sprite.JM_BRAKE_SMALL : Sprite.JM_BRAKE);
    }
    
    @Override
    public void rightPressed() {
    }

    @Override
    public void rightReleased() {
        if (jm.isFacingLeft()) {
            jm.setState(new Decelerating(jm, Decelerating.Type.TO_STAND));
        }
    }

    @Override
    public void leftPressed() {
    }

    @Override
    public void leftReleased() {
        if (jm.isFacingRight()) {
            jm.setState(new Decelerating(jm, Decelerating.Type.TO_STAND));
        }
    }

    @Override
    public void downPressed() {
        
    }
    
    @Override
    public void downReleased() {
        
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
        jm.setState(new Jumping(jm, Jumping.Type.BRAKE_ON_LANDING));
    }

    @Override
    public void jumpReleased() {
    }

    @Override
    public void checkEvents(double t) {
        if (jm.isFacingRight() && jm.getVelocity().getX() <= 0) {
            jm.setState(new Standing(jm));
            jm.faceLeft();
            
            if (jm.isReadyToRun()) {
                jm.setState(new Running(jm));
            } else {
                jm.setState(new Walking(jm));
            }
        } else if (jm.isFacingLeft() && jm.getVelocity().getX() >= 0) {
            jm.setState(new Standing(jm));
            jm.faceRight();
            
            if (jm.isReadyToRun()) {
                jm.setState(new Running(jm));
            } else {
                jm.setState(new Walking(jm));
            }
        }
    }   
}
