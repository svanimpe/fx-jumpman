package nodebinding;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import view.View;

public class BottomCenterBinding implements NodeBinding {

    @Override
    public void bind(Node node, DoubleProperty x, DoubleProperty y) {
        View view = View.getInstance();
        
        double width = node.getBoundsInLocal().getWidth();
        double height = node.getBoundsInLocal().getHeight();

        node.translateXProperty().unbind();
        node.translateXProperty().bind(x.subtract(view.xProperty()).subtract(width / 2));

        node.translateYProperty().unbind();
        node.translateYProperty().bind(View.HEIGHT.add(view.yProperty()).subtract(y).subtract(height));
    }
}
