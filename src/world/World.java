package world;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import objects.Fireball;
import objects.Goal;
import objects.Jumpman;
import objects.ScrollingBackground;
import states.jumpman.Dead;
import states.jumpman.Jumping;
import states.jumpman.Victorious;
import util.Vector2D;
import view.View;

public class World {
    
    /* OBJECTS */
    
    private ScrollingBackground background;
    
    public ScrollingBackground getBackground() {
        return background;
    }
    
    private Goal goal;
    
    public Goal getGoal() {
        return goal;
    }
    
    private Jumpman jumpman;
    
    public Jumpman getJumpman() {
        return jumpman;
    }
        
    private ObservableList<Fireball> fireballs;
    
    public ObservableList<Fireball> getFireballs() {
        return fireballs;
    }
    
    /* LEVEL */
    
    private Level level;
    
    public Level getLevel() {
        return level;
    }
    
    public void initializeLevel(String levelName) {
        level = Level.fromFile(levelName);
        background = new ScrollingBackground();
        goal = new Goal(level.getLength());
        jumpman = new Jumpman();
        fireballs = FXCollections.observableArrayList();
        levelTime.set(0);
    }
    
    private DoubleProperty levelTime = new SimpleDoubleProperty(0);

    public DoubleProperty levelTimeProperty() {
        return levelTime;
    }

    /* GAME LOOP */
    
    private final Duration frameLength = Duration.millis(1000 / 50);
    private final Timeline loop = TimelineBuilder.create()
            .cycleCount(Animation.INDEFINITE)
            .keyFrames(new KeyFrame(frameLength, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    update();
                }
            }))
            .build();
    
    private boolean paused = true;
    
    public void play() {
        loop.play();
        paused = false;
    }
    
    public void pause() {
        loop.pause();
        paused = true;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    private void update() {   
        goal.update(frameLength.toSeconds());
        
        jumpman.update(frameLength.toSeconds());
        for (Fireball f : fireballs) {
            f.update(frameLength.toSeconds());
        } 
        
        View.getInstance().scrollWindow();
        
        List<Fireball> toRemove = new ArrayList<>();
        for (Fireball f : fireballs) {
            if (jumpman.getNode().getBoundsInParent().intersects(f.getNode().getBoundsInParent())) {
                jumpman.takeHit();
            }
            
            if (f.getPosition().getX() + f.getNode().getBoundsInLocal().getWidth() < View.getInstance().getX()) {
                toRemove.add(f);
            }
        }
        fireballs.removeAll(toRemove);
        
        List<Launch> launches = level.getLaunchesForX(View.getInstance().getX() + View.WIDTH.get());
        for (Launch l : launches) {
            fireballs.add(new Fireball(new Vector2D(l.getX(), l.getY()), new Vector2D(l.getVelocity(), 0)));
        }
        
        if (jumpman.getPosition().getX() >= level.getLength() && ! (jumpman.getState() instanceof Jumping) && ! (jumpman.getState() instanceof Victorious) && ! (jumpman.getState() instanceof Dead)) {
            levelTime.set(levelTime.get() + frameLength.toSeconds());
            jumpman.setState(new Victorious(jumpman));
        } else if (!((jumpman.getState() instanceof Victorious) || (jumpman.getState() instanceof Dead))) {
            levelTime.set(levelTime.get() + frameLength.toSeconds());
        }
    }

    /* SINGLETON */
    
    private static final World instance = new World();
    
    private World() { }
    
    public static World getInstance() {
        return instance;
    }
}
