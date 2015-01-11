package view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.util.Duration;
import world.World;

public class Help extends StackPane {
    
    public Help() {
        Rectangle background = RectangleBuilder.create()
                .width(460)
                .height(300)
                .arcWidth(20)
                .arcHeight(20)
                .fill(Color.rgb(0, 0, 0, 0.5))
                .build();
        Text text = TextBuilder.create()
                .text("Welcome to Jumpman !\n" + 
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
                        "Press the escape key to close this window and start playing.")
                .font(Font.font("Arial", FontWeight.NORMAL, 14))
                .fill(Color.WHITE)
                .build();
        
        getChildren().addAll(background, text);
        setOpacity(0);
        
        AnchorPane root = View.getInstance().getRoot();
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
            fadeIn = FadeTransitionBuilder.create()
                .node(this)
                .toValue(1)
                .duration(Duration.seconds(1 - getOpacity()))
                .build();
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
            fadeOut = FadeTransitionBuilder.create()
                .node(this)
                .toValue(0)
                .duration(Duration.seconds(getOpacity()))
                .build();
            fadeOut.play();
            showing = false;
        }
    }
}
