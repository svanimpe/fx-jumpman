package states.jumpman;

import objects.Jumpman;
import util.Vector2D;

public class Running implements JumpmanState {

    private final Jumpman jm;
    private double timeElapsed = 0;

    public Running(Jumpman jm) {
        this.jm = jm;
        jm.setReadyToRun(true);
        jm.setAcceleration(jm.isFacingRight() ? Jumpman.ACCEL : Jumpman.ACCEL.flip());
    }
    
    @Override
    public void rightPressed() {
    }

    @Override
    public void rightReleased() {
        if (jm.isFacingRight()) {
            jm.setState(new Decelerating(jm, Decelerating.Type.TO_STAND));
        }
    }

    @Override
    public void leftPressed() {
    }

    @Override
    public void leftReleased() {
        if (jm.isFacingLeft()) {
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
    }

    @Override
    public void runReleased() {
        jm.setReadyToRun(false);
        jm.setState(new Decelerating(jm, Decelerating.Type.TO_WALK));
    }

    @Override
    public void jumpPressed() {
        jm.setState(new Jumping(jm, Jumping.Type.GO_ON_LANDING));
    }

    @Override
    public void jumpReleased() {
    }

    @Override
    public void checkEvents(double t) {
        timeElapsed += t;    
        if (timeElapsed > 0.05) {
            jm.rotateMovementSprites();
            timeElapsed = 0;
        }

        if (jm.isFacingRight() && jm.getVelocity().getX() > Jumpman.RUN_MAX.getX()) {
            jm.setVelocity(Jumpman.RUN_MAX);
            jm.setAcceleration(Vector2D.ZERO);
        } else if (jm.isFacingLeft() && jm.getVelocity().getX() < Jumpman.RUN_MAX.flip().getX()) {
            jm.setVelocity(Jumpman.RUN_MAX.flip());
            jm.setAcceleration(Vector2D.ZERO);
        }
    }   
}
