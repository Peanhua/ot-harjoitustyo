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
