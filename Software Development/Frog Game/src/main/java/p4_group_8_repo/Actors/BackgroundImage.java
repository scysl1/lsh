package p4_group_8_repo.Actors;

import javafx.scene.image.Image;

/**
 * The background image
 */
public class BackgroundImage extends Actor {

    @Override
    public void act(long now) {
    }

    /**
     * construct a instantiation of background image containing a directory
     *
     * @param imageLink the link to the background image
     * @throws IllegalArgumentException if imagelink is null
     */
    public BackgroundImage(String imageLink) throws IllegalArgumentException {
        if (imageLink == null)
            throw new IllegalArgumentException("'imagelink' should not be null");
        setImage(new Image(imageLink, 600, 800, true, true));

    }

}
