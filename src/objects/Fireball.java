package objects;

import nodebinding.BottomLeftBinding;
import util.Sprite;
import util.Vector2D;

public class Fireball extends GameObject {
    
    public Fireball(Vector2D initialPosition, Vector2D initialVelocity) {
        super(Sprite.FIRE1, new BottomLeftBinding());
        setPosition(initialPosition);
        setVelocity(initialVelocity);
    }

    private double timeElapsed = 0;
    
    @Override
    public void update(double t) {
        super.update(t);
        
        timeElapsed += t;
        if (timeElapsed > 0.10) {
            if (getSprite() == Sprite.FIRE1) {
                setSprite(Sprite.FIRE2);
            } else if (getSprite() == Sprite.FIRE2) {
                setSprite(Sprite.FIRE3);
            } else {
                setSprite(Sprite.FIRE1);
            }
            timeElapsed = 0;
        }
    }
}
