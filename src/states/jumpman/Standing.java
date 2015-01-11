package states.jumpman;

import objects.Jumpman;
import util.Sprite;
import util.Vector2D;

public class Standing implements JumpmanState {

    private final Jumpman jm;
    
    public Standing(Jumpman jm) {
        this.jm = jm;
        jm.setVelocity(Vector2D.ZERO);
        jm.setAcceleration(Vector2D.ZERO);
        jm.setSprite(jm.isSmall() ? Sprite.JM_STAND_SMALL : Sprite.JM_STAND);
    }
    
    @Override
    public void rightPressed() {
        jm.faceRight();
        if (jm.isReadyToRun()) {
            jm.setState(new Running(jm));
        } else {
            jm.setState(new Walking(jm));
        }
    }

    @Override
    public void rightReleased() {
    }

    @Override
    public void leftPressed() {
        jm.faceLeft();
        if (jm.isReadyToRun()) {
            jm.setState(new Running(jm));
        } else {
            jm.setState(new Walking(jm));
        }
    }

    @Override
    public void leftReleased() {
    }

    @Override
    public void downPressed() {
        jm.setState(new Ducking(jm));
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
        jm.setState(new Jumping(jm, Jumping.Type.DECEL_ON_LANDING));
    }

    @Override
    public void jumpReleased() {
    }

    @Override
    public void checkEvents(double t) {
    }   
}
