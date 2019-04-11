/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import java.net.URL;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

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
    
    
    private final HashMap<String, Image>                images;
    private final HashMap<String, Image>                partialImages;
    private final HashMap<Image, HashMap<Image, Image>> combinedImages;
  
    private ImageCache() {
        this.images         = new HashMap<>();
        this.partialImages  = new HashMap<>();
        this.combinedImages = new HashMap<>();
    }
    
    public Image get(String name) {
        Image image = this.images.get(name);
        if (image != null) {
            return image;
        }
        
        if (this.images.containsKey(name)) {
            return null;
        }
        
        String fullname = "fishingrodofdestiny/" + name + ".png";
        URL imageURL = getClass().getClassLoader().getResource(fullname);
        if (imageURL != null) {
            image = new Image(imageURL.toExternalForm(), false);
        }
        
        this.images.put(name, image);
        
        if (image == null || image.isError()) {
            System.out.println("Failed to load " + fullname);
            return null;
        }

        return image;
    }
    
    
    public Image getPartial(String name, int offsetX, int offsetY, int width, int height) {
        String key = name + ":" + offsetX + "," + offsetY + "-" + width + "x" + height;
        Image partialImage = this.partialImages.get(key);
        if (partialImage != null) {
            return partialImage;
        }
        
        if (this.partialImages.containsKey(key)) {
            return null;
        }
        
        Image source = this.get(name);
        if (source == null) {
            return null;
        }
        
        partialImage = this.createPartial(source, offsetX, offsetY, width, height);
        this.partialImages.put(key, partialImage);
        
        return partialImage;
    }
    
    
    public Image getCombined(Image background, Image foreground) {
        HashMap<Image, Image> combinedWithBackgrounds = this.combinedImages.get(background);
        if (combinedWithBackgrounds == null) {
            combinedWithBackgrounds = new HashMap<>();
            this.combinedImages.put(background, combinedWithBackgrounds);
        }
        
        Image combined = combinedWithBackgrounds.get(foreground);
        if (combined != null) {
            return combined;
        }
        
        if (combinedWithBackgrounds.containsKey(foreground)) {
            return null;
        }

        combined = this.combineImages(background, foreground);
        combinedWithBackgrounds.put(foreground, combined);
        
        return combined;
    }



    private Image createPartial(Image source, int offsetX, int offsetY, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        
        PixelReader reader = source.getPixelReader();
        PixelWriter writer = image.getPixelWriter();
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                writer.setColor(x, y, reader.getColor(offsetX + x, offsetY + y));
            }
        }
        
        return image;
    }

    
    
    private Image combineImages(Image background, Image foreground) {
        int width = (int) background.getWidth();
        int height = (int) background.getHeight();
        
        WritableImage combined = new WritableImage(width, height);
        
        PixelReader prBackground = background.getPixelReader();
        PixelReader prForeground = foreground.getPixelReader();
        PixelWriter pwCombined   = combined.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color bg = prBackground.getColor(x, y);
                Color fg = prForeground.getColor(x, y);
                
                double a = fg.getOpacity();
                double r = (1.0 - a) * bg.getRed()   + a * fg.getRed();
                double g = (1.0 - a) * bg.getGreen() + a * fg.getGreen();
                double b = (1.0 - a) * bg.getBlue()  + a * fg.getBlue();

                pwCombined.setColor(x, y, new Color(r, g, b, 1));
            }
        }
        
        return combined;
    }
}
