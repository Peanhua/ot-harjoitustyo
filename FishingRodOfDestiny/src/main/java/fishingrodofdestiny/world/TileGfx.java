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
    private final List<Image> images;
    private final int         offsetX;
    private final int         offsetY;
    private final int         width;
    private final int         height;
    private int               currentFrame;

    public TileGfx(int gfxOffsetX, int gfxOffsetY, int gfxWidth, int gfxHeight) {
        this.images       = new ArrayList<>();
        this.offsetX      = gfxOffsetX;
        this.offsetY      = gfxOffsetY;
        this.width        = gfxWidth;
        this.height       = gfxHeight;
        this.currentFrame = -1;
    }

    public TileGfx(String gfxFilename, int gfxOffsetX, int gfxOffsetY, int gfxWidth, int gfxHeight) {
        this(gfxOffsetX, gfxOffsetY, gfxWidth, gfxHeight);
        this.images.add(ImageCache.getInstance().get(gfxFilename));
    }
     
    public TileGfx(List<String> gfxFilenames, int gfxOffsetX, int gfxOffsetY, int gfxWidth, int gfxHeight) {
        this(gfxOffsetX, gfxOffsetY, gfxWidth, gfxHeight);
        for (String filename : gfxFilenames) {
            this.images.add(ImageCache.getInstance().get(filename));
        }
    }
    
    
    public void draw(GraphicsContext context, int x, int y, int size) {
        context.drawImage(this.getNextFrame(), this.offsetX, this.offsetY, this.width, this.height, x, y, size, size);
    }
    
    public Image getNextFrame() {
        this.currentFrame++;
        if (this.currentFrame >= this.images.size()) {
            this.currentFrame = 0;
        }
        return this.images.get(this.currentFrame);
    }
}
