package util;

import javafx.scene.image.Image;

public enum Sprite {
    
    BACKGROUND("/resources/images/background.png"),
    JM_STAND("/resources/images/jm_stand.png"),
    JM_STAND_SMALL("/resources/images/jm_stand_small.png"),
    JM_STEP1("/resources/images/jm_step1.png"),
    JM_STEP1_SMALL("/resources/images/jm_step1_small.png"),
    JM_STEP2("/resources/images/jm_step2.png"),
    JM_STEP2_SMALL("/resources/images/jm_step2_small.png"),
    JM_BRAKE("/resources/images/jm_brake.png"),
    JM_BRAKE_SMALL("/resources/images/jm_brake_small.png"),
    JM_JUMP("/resources/images/jm_jump.png"),
    JM_JUMP_SMALL("/resources/images/jm_jump_small.png"),
    JM_DUCK("/resources/images/jm_duck.png"),
    JM_DUCK_SMALL("/resources/images/jm_duck_small.png"),
    JM_VICTORY("/resources/images/jm_victory.png"),
    JM_VICTORY_SMALL("/resources/images/jm_victory_small.png"),
    JM_DEATH("/resources/images/jm_death.png"),
    FIRE1("/resources/images/fire1.png"),
    FIRE2("/resources/images/fire2.png"),
    FIRE3("/resources/images/fire3.png"),
    GOAL1("/resources/images/goal1.png"),
    GOAL2("/resources/images/goal2.png"),
    GOAL3("/resources/images/goal3.png"),
    GOAL4("/resources/images/goal4.png");
    
    private final Image image;
    
    private Sprite(String path) {
        image = new Image(getClass().getResource(path).toExternalForm());
    }
    
    public Image getImage() {
        return image;
    }
}
