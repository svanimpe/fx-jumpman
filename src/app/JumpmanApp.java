package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.View;
import world.World;

public class JumpmanApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        World.getInstance().initializeLevel("demo");
        View.getInstance().initializeView();
        View.getInstance().loadView();
        
        Scene scene = new Scene(View.getInstance().getRoot(), View.WIDTH.get() * 2, View.HEIGHT.get() * 2);

        stage.setScene(scene);
        stage.setTitle("Jumpman (" + World.getInstance().getLevel().getName() + ")");
        stage.show();
        
        View.getInstance().getHelp().show();
    }
}
