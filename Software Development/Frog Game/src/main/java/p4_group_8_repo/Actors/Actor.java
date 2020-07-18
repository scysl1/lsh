package p4_group_8_repo.Actors;

import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;

import java.util.ArrayList;

/**
 * The actor in the game, including all game elements
 */
public abstract class Actor extends ImageView{
    /**
     * the picture path
     */
    String picpath = "file:src/main/java/p4_group_8_repo/pic/";

    /**
     * method for control element to move
     * @param dx change of xpos per move
     * @param dy change of ypos per move
     */
    void move(double dx, double dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /**
     * Get the parent world
     * @return the world
     */
    private World getWorld() {
        return (World) getParent();
    }

    /**
     * Get the width
     * @return the width of local scene
     */
    public double getWidth() {
        return this.getBoundsInLocal().getWidth();
    }

    /**
     * Get the height
     * @return get the height of local scene
     */
    public double getHeight() {
        return this.getBoundsInLocal().getHeight();
    }

    /**
     * Judge if the animal has intersect with objects
     * @param cls class
     * @param <A> find array of actor
     * @return return the actor array
     */
    <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
            }
        }
        return someArray;
    }
    
    public void manageInput() { }
    /**
     * Judge if the animal has intersect with one object
     * @param cls class
     * @param <A> find array of actor
     * @return return the actor array
     */
    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
        ArrayList<A> someArray = new ArrayList<A>();
        for (A actor: getWorld().getObjects(cls)) {
            if (actor != this && actor.intersects(this.getBoundsInLocal())) {
                someArray.add(actor);
                break;
            }
        }
        return someArray.get(0);
    }

    /**
     * the action method
     * @param now the current time
     */
    public abstract void act(long now);



}
