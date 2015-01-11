package states.jumpman;

import objects.Jumpman;
import util.Sprite;
import util.Vector2D;

public class Victorious implements JumpmanState {

    private final Jumpman jm;
    private double timeElapsed = 0;
    private boolean stopped = false;
    
    public Victorious(Jumpman jm) {
        this.jm = jm;
        jm.setAcceleration(Jumpman.DECEL);
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
    public void checkEvents(double t) {        
        if (jm.getVelocity().getX() > 0) {
            timeElapsed += t;
            if (timeElapsed > 0.075) {
                jm.rotateMovementSprites();
                timeElapsed = 0;
            }
        } else if (!stopped) {
            jm.setVelocity(Vector2D.ZERO);
            jm.setAcceleration(Vector2D.ZERO);
            jm.setSprite(jm.isSmall() ? Sprite.JM_VICTORY_SMALL : Sprite.JM_VICTORY);
            stopped = true;
        }
    }
}
