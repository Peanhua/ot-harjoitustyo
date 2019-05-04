/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * <p>
 * Three types of images can be obtained:
 * <ul>
 *   <li>Complete image - The whole image with the given name.</li>
 *   <li>Partial image - A rectangular portion of a source image.</li>
 *   <li>Combined image - Two images combined together using alpha values.</li>
 * </ul>
 * 
 * @author joyr
 */
public class ImageCache {
    private static ImageCache instance = null;
    
    /**
     * Returns the singleton instance for the ImageCache.
     * 
     * @return Singleton instance of ImageCache.
     */
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
    
    /**
     * Obtain image of the given name.
     * <p>
     * Returns the image associated to the given name. The image will be loaded if it is not yet in memory. There is a one-to-one mapping between names and image objects.
     * Image loading is performed using getResource() from fishingrodofdestiny -package.
     * 
     * @see #getPartial(String, int, int, int, int)
     * @see #getCombined(Image, Image)
     * 
     * @param name The name of the image.
     * @return The image object, or null if the loading failed.
     */
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
    
    /**
     * Return partial portion of an image.
     * <p>
     * A rectangular portion of the source image is returned. These partial portions are separately cached, thus calling getPartial() twice with same parameters returns the same image object.
     * 
     * @see #get(String)
     * @see #getCombined(Image, Image)
     * 
     * @param name    The name of the image.
     * @param offsetX The top left corner of the partial image, X coordinate in the source image.
     * @param offsetY The top left corner of the partial image, Y coordinate in the source image.
     * @param width   The width of the rectangle.
     * @param height  The height of the rectangle.
     * @return The image object, or null if the loading failed.
     */
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
    

    /**
     * Return the background and the foreground images combined as one image.
     * <p>
     * The given background and foreground images are combined together and returned.
     * The foreground image is placed on top of the background image using the alpha values from the foreground image.
     * For pixels with zero alpha, the background is completely visible. And for pixels with full alpha (1.0), the foreground is completely visible.
     * <p>
     * Assumes that the background has full alpha (1.0) for all pixels.
     * 
     * @see #get(String)
     * @see #getPartial(String, int, int, int, int)
     * 
     * @param background The background image.
     * @param foreground The foreground image.
     * @return The combined image.
     */
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
