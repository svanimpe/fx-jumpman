package view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Group;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import objects.Fireball;
import objects.Jumpman;
import world.World;

public class View {
    
    /* SIZE */
    
    public static final ReadOnlyDoubleProperty WIDTH = new ReadOnlyDoubleWrapper(480).getReadOnlyProperty();
    public static final ReadOnlyDoubleProperty HEIGHT = new ReadOnlyDoubleWrapper(240).getReadOnlyProperty();
    
    /* LIMITS FOR SCROLLING */
    
    public static final ReadOnlyDoubleProperty LIMIT_LOW = new ReadOnlyDoubleWrapper(80).getReadOnlyProperty();
    public static final ReadOnlyDoubleProperty LIMIT_HIGH = new ReadOnlyDoubleWrapper(240).getReadOnlyProperty();
    
    /* POSITION IN WORLD (BOTTOM LEFT CORNER) */
    
    private final DoubleProperty x = new SimpleDoubleProperty(-80);
    private final DoubleProperty y = new SimpleDoubleProperty(-32);

    public double getX() {
        return x.get();
    }

    public double getY() {
        return y.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }
    
    public void setY(double y) {
        this.y.set(y);
    }
    
    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
    }
    
    public void scrollWindow() {
        Jumpman jm = World.getInstance().getJumpman();
        
        if (jm.getPosition().getX() - x.get() > LIMIT_HIGH.get()) {
            x.set(x.get() + (jm.getPosition().getX() - x.get() - LIMIT_HIGH.get()));
        } else if (jm.getPosition().getX() - x.get() < LIMIT_LOW.get()) {
            x.set(x.get() - (LIMIT_LOW.get() - (jm.getPosition().getX() - x.get())));
        }
    }
    
    /* NODES */
    
    private AnchorPane root;
    private Group view;
    private Help help;
    
    public AnchorPane getRoot() {
        return root;
    }
    
    public void initializeView() {
        root = new AnchorPane();
        view = new Group();
        
        Scale viewScale = Transform.scale(1, 1, 0, 0);
        viewScale.xProperty().bind(root.widthProperty().divide(View.WIDTH));
        viewScale.yProperty().bind(root.heightProperty().divide(View.HEIGHT));
        view.getTransforms().add(viewScale);
        
        view.setFocusTraversable(true);
        view.addEventHandler(KeyEvent.ANY, new Controller());
        
        root.getChildren().add(view);
        
        Text info = TextBuilder.create()
                .text("Esc: Help\nR: Reset")
                .font(Font.font("Arial", FontWeight.BOLD, 16))
                .fill(Color.WHITE)
                .build();
        AnchorPane.setTopAnchor(info, 20.0);
        AnchorPane.setRightAnchor(info, 20.0);
        
        root.getChildren().add(info);
        
        Text time = TextBuilder.create()
                .font(Font.font("Arial", FontWeight.BOLD, 16))
                .fill(Color.WHITE)
                .build();
        time.textProperty().bind(Bindings.concat("Time: ", Bindings.format("%.2f", World.getInstance().levelTimeProperty()), " seconds"));
        
        AnchorPane.setTopAnchor(time, 20.0);
        AnchorPane.setLeftAnchor(time, 20.0);
        
        root.getChildren().add(time);
        
        help = new Help();
        root.getChildren().add(help);
    }
    
    public void clearView() {
        view.getChildren().clear();
    }
    
    public void loadView() {
        World w = World.getInstance();
        
        view.getChildren().add(w.getBackground().getNode());
        view.getChildren().add(w.getGoal().getNode());
        view.getChildren().add(w.getJumpman().getNode());

        for (Fireball f : w.getFireballs()) {
            view.getChildren().add(f.getNode());
        }
        
        w.getFireballs().addListener(new ListChangeListener<Fireball>() {
            @Override
            public void onChanged(Change<? extends Fireball> change) {
                while (change.next()) {
                    for (Fireball f : change.getAddedSubList()) {
                        view.getChildren().add(f.getNode());
                    }
                    for (Fireball f : change.getRemoved()) {
                        view.getChildren().remove(f.getNode());
                    }
                }
            } 
        });
        
        x.set(-80);
        y.set(-32);
    }
    
    public Help getHelp() {
        return help;
    }
    
    /* SINGLETON */
    
    private static final View instance = new View();
    
    private View() { }
    
    public static View getInstance() {
        return instance;
    }
}
