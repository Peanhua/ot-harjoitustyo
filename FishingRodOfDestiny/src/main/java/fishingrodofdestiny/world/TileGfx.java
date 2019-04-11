/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.resources.ImageCache;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author joyr
 */
public class TileGfx {
    private final List<Image> sourceImages;
    private final List<Image> images;
    private Image             background;
    private int               currentFrame;

    private TileGfx() {
        this.sourceImages = new ArrayList<>();
        this.images       = new ArrayList<>();
        this.background   = null;
        this.currentFrame = -1;
    }

    public TileGfx(String gfxFilename, int gfxOffsetX, int gfxOffsetY, int gfxWidth, int gfxHeight) {
        this();
        this.sourceImages.add(ImageCache.getInstance().getPartial(gfxFilename, gfxOffsetX, gfxOffsetY, gfxWidth, gfxHeight));
        this.sourceImages.forEach(image -> this.images.add(image));
    }
     
    public TileGfx(List<String> gfxFilenames, int gfxOffsetX, int gfxOffsetY, int gfxWidth, int gfxHeight) {
        this();
        gfxFilenames.forEach(filename ->
            this.sourceImages.add(ImageCache.getInstance().getPartial(filename, gfxOffsetX, gfxOffsetY, gfxWidth, gfxHeight))
        );
        this.sourceImages.forEach(image -> this.images.add(image));
    }
    
    
    public final void setBackground(Image background) {
        if (this.background == background) {
            return;
        }
        
        this.background = background;
        this.images.clear();

        if (this.background == null) {
            this.sourceImages.forEach(image -> this.images.add(image));
            
        } else {
            ImageCache cache = ImageCache.getInstance();
            this.sourceImages.forEach(image -> {
                this.images.add(cache.getCombined(this.background, image));
            });
        }
    }
    
    public void draw(GraphicsContext context, int x, int y, int size) {
        context.drawImage(this.getNextFrame(), x, y, size, size);
    }
    
    public Image getNextFrame() {
        this.currentFrame++;
        if (this.currentFrame >= this.images.size()) {
            this.currentFrame = 0;
        }
        return this.images.get(this.currentFrame);
    }
}
