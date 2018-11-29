package view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import world.World;

public class Help extends StackPane {
    
    public Help() {
        var background = new Rectangle();
        background.setWidth(460);
        background.setHeight(300);
        background.setArcWidth(20);
        background.setArcHeight(20);
        background.setFill(Color.rgb(0, 0, 0, 0.5));
        
        var text = new Text("Welcome to Jumpman !\n" + 
                "Avoid the fireballs and make it to the goal as fast as possible.\n" +
                "You can afford getting hit once, but not twice !\n\n" +
                "Jumpman has the following skills:\n\n" +
                " * Walk: by pressing the left or right arrow keys.\n" +
                " * Run: by holding the F key while walking.\n" +
                " * Brake: by pressing the opposite arrow key while moving.\n" +
                " * Duck: by pressing the down arrow key.\n" +
                " * Jump: by pressing the space key.\n\n" +
                "Holding space longer will make Jumpman jump higher.\n" +
                "Pressing more than one arrow key at the same time will not work.\n\n" +
                "Press the escape key to close this window and start playing.");
        text.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        text.setFill(Color.WHITE);
        
        getChildren().addAll(background, text);
        setOpacity(0);
        
        var root = View.getInstance().getRoot();
        translateXProperty().bind(root.widthProperty().divide(2).subtract(widthProperty().divide(2)));
        translateYProperty().bind(root.heightProperty().divide(2).subtract(heightProperty().divide(2)));
    }

    /* SHOW AND HIDE */
    
    private boolean showing = false;
    
    private FadeTransition fadeIn;
    private FadeTransition fadeOut;
    
    public boolean isShowing() {
        return showing;
    }
    
    public void show() {
        if (!showing) {
            World.getInstance().pause();
            if (fadeOut != null && fadeOut.getStatus() != Animation.Status.STOPPED) {
                fadeOut.stop();
            }
            fadeIn = new FadeTransition();
            fadeIn.setNode(this);
            fadeIn.setToValue(1);
            fadeIn.setDuration(Duration.seconds(1 - getOpacity()));
            fadeIn.play();
            showing = true;
        }
    }
    
    public void hide() {
        if (showing) {
            World.getInstance().play();
            if (fadeIn != null && fadeIn.getStatus() != Animation.Status.STOPPED) {
                fadeIn.stop();
            }
            fadeOut = new FadeTransition();
            fadeOut.setNode(this);
            fadeOut.setToValue(0);
            fadeOut.setDuration(Duration.seconds(getOpacity()));
            fadeOut.play();
            showing = false;
        }
    }
}
