package states.jumpman;

public interface JumpmanState {
    
    void rightPressed();
    void rightReleased();
    
    void leftPressed();
    void leftReleased();
    
    void downPressed();
    void downReleased();
    
    void runPressed();
    void runReleased();
    
    void jumpPressed();
    void jumpReleased();

    void checkEvents(double t);
}
