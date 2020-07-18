package p4_group_8_repo.Actors;
import javafx.scene.image.Image;

/**
 * physical stuff which includes actors who can move and have behaviors
 */
public class physicalstuff extends Actor implements physicalstuff_initialisation{

    double speed;
    private int xpos;
    private int ypos;
    private int w;
    private int h;
    private String imgaeLink;
    /**
     * The number for showing image threading
     */
    int magicnumber = 900000000;
    @Override
    public void act(long now) {}
    void physicalAct(long now, int boundary) {
        set_Image(now);
        move(getSpeed() , 0);
        if (getX() > 600 && getSpeed()>0)
            setX(-200);
        if (getX() < -boundary && getSpeed()<0)
            setX(600);
    }
    public void set_Image(long now){}
    public void setSpeed(double speed){ this.speed = speed; }
    public double getSpeed(){
        return this.speed;
    }
    public int getXpos() {
        return xpos;
    }
    public void setXpos(int xpos) { this.xpos = xpos; setX(xpos); }
    public int getYpos() {
        return ypos;
    }
    public void setYpos(int ypos) { this.ypos = ypos; setY(ypos); }
    public int getW() {
        return w;
    }
    public void setW(int w) { this.w = w; }
    public int getH() {
        return h;
    }
    public void setH(int h) {
        this.h = h;
    }
    public String getImgaeLink() {
        return imgaeLink;
    }
    public void setImgaeLink(String imgaeLink) {
        this.imgaeLink = imgaeLink;
    }
    public void set_Image(String imageLink, int w, int h){
        setImage(new Image(imageLink, w, h,true, true));
    }
}
