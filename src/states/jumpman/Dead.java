package states.jumpman;

import objects.Jumpman;
import util.Sprite;
import util.Vector2D;

public class Dead implements JumpmanState{

    private final Jumpman jm;
    
    public Dead(Jumpman jm) {
        this.jm = jm;
        jm.setVelocity(Vector2D.ZERO);
        jm.setAcceleration(Vector2D.ZERO);
        jm.setSprite(Sprite.JM_DEATH);
    }
    
    @Override
    public void rightPressed() { }

    @Override
    public void rightReleased() { }

    @Override
    public void leftPressed() { }

    @Override
    public void leftReleased() { }

    @Override
    public void downPressed() { }

    @Override
    public void downReleased() { }

    @Override
    public void runPressed() { }

    @Override
    public void runReleased() { }

    @Override
    public void jumpPressed() { }

    @Override
    public void jumpReleased() { }

    @Override
    public void checkEvents(double t) { }
}
