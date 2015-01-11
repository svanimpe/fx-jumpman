package states.jumpman;

import objects.Jumpman;
import util.Vector2D;

public class Walking implements JumpmanState {

    private final Jumpman jm;
    private double timeElapsed = 0;;
    
    public Walking(Jumpman jm) {
        this.jm = jm;
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
        jm.setState(new Running(jm));
    }

    @Override
    public void runReleased() {
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
        if (timeElapsed > 0.10) {
            jm.rotateMovementSprites();
            timeElapsed = 0;
        }

        if (jm.isFacingRight() && jm.getVelocity().getX() > Jumpman.WALK_MAX.getX()) {
            jm.setVelocity(Jumpman.WALK_MAX);
            jm.setAcceleration(Vector2D.ZERO);
        } else if (jm.isFacingLeft() && jm.getVelocity().getX() < Jumpman.WALK_MAX.flip().getX()) {
            jm.setVelocity(Jumpman.WALK_MAX.flip());
            jm.setAcceleration(Vector2D.ZERO);
        }
    }   
}
