package objects;

import nodebinding.BottomLeftBinding;
import util.Sprite;
import util.Vector2D;

public class Goal extends GameObject {

    public Goal(double initialPosition) {
        super(Sprite.GOAL1, new BottomLeftBinding());
        setPosition(new Vector2D(initialPosition, 0));
    }

    private double timeElapsed = 0;
    
    @Override
    public void update(double t) {        
        timeElapsed += t;
        if (timeElapsed > 0.15) {
            if (getSprite() == Sprite.GOAL1) {
                setSprite(Sprite.GOAL2);
            } else if (getSprite() == Sprite.GOAL2) {
                setSprite(Sprite.GOAL3);
            } else if (getSprite() == Sprite.GOAL3) {
                setSprite(Sprite.GOAL4);
            } else {
                setSprite(Sprite.GOAL1);
            }
            timeElapsed = 0;
        }
    }
}
