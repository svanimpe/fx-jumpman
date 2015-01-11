package states.jumpman;

import objects.Jumpman;

public class Decelerating implements JumpmanState {

    public enum Type { TO_WALK, TO_STAND };
    
    private final Jumpman jm;
    private double timeElapsed = 0;
    private Type type;
    
    public Decelerating(Jumpman jm, Type type) {
        this.jm = jm;
        this.type = type;
        jm.setAcceleration(jm.isFacingRight() ? Jumpman.DECEL : Jumpman.DECEL.flip());
    }
    
    @Override
    public void rightPressed() {
        if (jm.isFacingRight() && type == Type.TO_STAND && !jm.isReadyToRun()) {
            type = Type.TO_WALK;
        } else if (jm.isFacingRight() && type == Type.TO_STAND && jm.isReadyToRun()) {
            jm.setState(new Running(jm));
        } else if (jm.isFacingLeft() && type == Type.TO_STAND) {
            jm.setState(new Braking(jm));
        }
    }

    @Override
    public void rightReleased() {
        if (jm.isFacingRight()) {
            type = Type.TO_STAND;
        }
    }

    @Override
    public void leftPressed() {
        if (jm.isFacingLeft() && type == Type.TO_STAND && !jm.isReadyToRun()) {
            type = Type.TO_WALK;
        } else if (jm.isFacingLeft() && type == Type.TO_STAND && jm.isReadyToRun()) {
            jm.setState(new Running(jm));
        } else if (jm.isFacingRight() && type == Type.TO_STAND) {
            jm.setState(new Braking(jm));
        }
    }

    @Override
    public void leftReleased() {
        if (jm.isFacingLeft()) {
            type = Type.TO_STAND;
        }
    }

    @Override
    public void downPressed() {
        if (type == Type.TO_STAND) {
            jm.setState(new Ducking(jm));
        }
    }
    
    @Override
    public void downReleased() {
        
    }
    
    @Override
    public void runPressed() {
        if (type == Type.TO_WALK) {
            jm.setState(new Running(jm));
        } else {
            jm.setReadyToRun(true);
        }
    }

    @Override
    public void runReleased() {
        jm.setReadyToRun(false);
    }

    @Override
    public void jumpPressed() {
        if (type == Type.TO_STAND) {
            jm.setState(new Jumping(jm, Jumping.Type.DECEL_ON_LANDING));
        } else if (type == Type.TO_WALK) {
            jm.setState(new Jumping(jm, Jumping.Type.GO_ON_LANDING));
        }
    }

    @Override
    public void jumpReleased() {
    }

    @Override
    public void checkEvents(double t) {
        timeElapsed += t;    
        if (timeElapsed > 0.075) {
            jm.rotateMovementSprites();
            timeElapsed = 0;
        }
        
        if (type == Type.TO_WALK && jm.isFacingRight() && jm.getVelocity().getX() <= Jumpman.WALK_MAX.getX()) {
            jm.setState(new Walking(jm));
        } else if (type == Type.TO_WALK && jm.isFacingLeft() && jm.getVelocity().getX() >= Jumpman.WALK_MAX.flip().getX()) {
            jm.setState(new Walking(jm));
        } else if (type == Type.TO_STAND && jm.isFacingRight() && jm.getVelocity().getX() <= 0) {
            jm.setState(new Standing(jm));
        } else if (type == Type.TO_STAND && jm.isFacingLeft() && jm.getVelocity().getX() >= 0) {
            jm.setState(new Standing(jm));
        }
    }   
}
