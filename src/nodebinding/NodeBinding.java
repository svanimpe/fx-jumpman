package nodebinding;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;

public interface NodeBinding {
    
    void bind(Node n, DoubleProperty x, DoubleProperty y);
}
