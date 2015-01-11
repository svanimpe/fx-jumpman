package objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Transform;
import nodebinding.NodeBinding;
import util.Sprite;
import util.Vector2D;

public class GameObject {
    
    public GameObject(Sprite sprite, NodeBinding binding) {
        this.binding = binding;
        setSprite(sprite);
    }
    
    /* POSITION, VELOCITY & ACCELERATION */
    
    private final DoubleProperty x = new SimpleDoubleProperty(0);
    private final DoubleProperty y = new SimpleDoubleProperty(0);    
    
    private Vector2D p = Vector2D.ZERO;
    private Vector2D v = Vector2D.ZERO;
    private Vector2D a = Vector2D.ZERO;
    
    public final Vector2D getPosition() {
        return p;
    }

    public final void setPosition(Vector2D p) {
        this.p = p;
        x.set(p.getX());
        y.set(p.getY());
    }
    
    public final Vector2D getVelocity() {
        return v;
    }
    
    public final void setVelocity(Vector2D v) {
        this.v = v;
    }
    
    public final Vector2D getAcceleration() {
        return a;
    }
    
    public final void setAcceleration(Vector2D a) {
        this.a = a;
    }
    
    /*
     * Can be overridden to add behaviour.
     */
    public void update(double t) {      
        setPosition(getPosition().add(v.times(t)).add(a.times(0.5 * t * t)));
        v = v.add(a.times(t));
    }
    
    /* NODE & SPRITE */

    private final ImageView node = new ImageView();
    private Sprite sprite;
    private final NodeBinding binding;
    
    public final Node getNode() {
        return node;
    }
    
    public final Sprite getSprite() {
        return sprite;
    }
    
    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
        node.setImage(sprite.getImage());
        binding.bind(node, x, y);
        
        boolean wasFlippedHorizontal = flippedHorizontal;
        
        clearTransforms();
        if (wasFlippedHorizontal) {
            flipHorizontal();
        }
    }
    
    /* TRANSFORMS */
    
    // TODO Transforms should take binding type into account.
    
    private boolean flippedHorizontal = false;
    
    public final void flipHorizontal() {
        node.getTransforms().add(Transform.scale(-1, 1, node.getBoundsInLocal().getWidth() / 2, node.getBoundsInLocal().getHeight() / 2));
        flippedHorizontal = true;
    }
    
    public final void clearTransforms() {
        node.getTransforms().clear();
        flippedHorizontal = false;
    }
}
