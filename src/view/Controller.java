package view;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import world.World;

public class Controller implements EventHandler<KeyEvent> {
    
    @Override
    public void handle(KeyEvent t) {
        
        if (t.getCode() == KeyCode.ESCAPE && t.getEventType() == KeyEvent.KEY_PRESSED) {
            View v = View.getInstance();
            if (v.getHelp().isShowing()) {
                v.getHelp().hide();
            } else {
                v.getHelp().show();
            }
        } else if (t.getCode() == KeyCode.R && t.getEventType() == KeyEvent.KEY_PRESSED) {
            View.getInstance().getHelp().show();
            View.getInstance().clearView();
            World.getInstance().initializeLevel("demo");
            View.getInstance().loadView();    
        } else if (t.getEventType() == KeyEvent.KEY_RELEASED || ! View.getInstance().getHelp().isShowing()) {
            World.getInstance().getJumpman().handle(t);
        }
    }
}
