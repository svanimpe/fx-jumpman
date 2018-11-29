package objects;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import util.Sprite;
import view.View;

public class ScrollingBackground {
    
    private final ImageView node = new ImageView();
    
    public ScrollingBackground() {
        node.setImage(Sprite.BACKGROUND.getImage());
        bindNode();
    }

    public Node getNode() {
        return node;
    }
    
    private void bindNode() {
        final var view = View.getInstance();
        
        node.translateXProperty().bind(new DoubleBinding() {

            {
                super.bind(view.xProperty());
            }
            
            @Override
            protected double computeValue() {
                double offset = view.getX() % View.WIDTH.get();
                if (offset < 0) {
                    offset += View.WIDTH.get();
                }
                return -offset;
            }
        });
        
        node.translateYProperty().bind(new SimpleDoubleProperty(32).add(view.yProperty()));
    }
}
