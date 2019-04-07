/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import java.util.HashMap;
import javafx.scene.image.Image;

/**
 * Singleton to load and cache images.
 *
 * @author joyr
 */
public class ImageCache {
    private static ImageCache instance = null;
    
    public static ImageCache getInstance() {
        if (ImageCache.instance == null) {
            ImageCache.instance = new ImageCache();
        }
        return ImageCache.instance;
    }
    
    
    private HashMap<String, Image> images;
  
    private ImageCache() {
        this.images = new HashMap<>();
    }
    
    public Image get(String name) {
        Image image = this.images.get(name);
        if (image != null) {
            return image;
        }
        
        if (this.images.containsKey(name)) {
            return null;
        }
        
        String fullname = "file:gfx/" + name + ".png";
        image = new Image(fullname, false);
        this.images.put(name, image);
        
        if (image.isError()) {
            System.out.println("Failed to load " + fullname);
            return null;
        }

        return image;
    }
}