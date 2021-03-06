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
package fishingrodofdestiny.ui.widgets;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;




/**
 * A simple visual effect of stars going past the screen from one edge of the screen to another edge.
 * 
 * @author joyr
 */
public class Starfield extends Widget {
    private Pane            container;
    private Canvas          canvas;
    private GraphicsContext graphicsContext;
    private int             width;
    private int             height;
    private Star[]          stars;
    private final Random    random;
    private AnimationTimer  animationTimer;
    private double          timeToNextStar;
    public final static int MAX_Z = 30;
    private Color           backgroundColor;
    private final Direction direction;


    public enum Direction { UP, DOWN, LEFT, RIGHT };
    
    public Starfield(Direction direction) {
        this.direction = direction;
        this.random = new Random();
        this.timeToNextStar = 0.0;
    }
    
    
    @Override
    public Node createUserInterface() {
        this.container = new Pane();
        this.container.setMaxWidth(Double.MAX_VALUE);
        this.container.setMaxHeight(Double.MAX_VALUE);
        
        this.width           = 0;
        this.height          = 0;
        this.canvas          = null;
        this.graphicsContext = null;
        this.stars           = new Star[100];
        this.backgroundColor = new Color(0.0, 0.0, 0.0, 0.0);
        
        this.container.heightProperty().addListener((o) -> {
            this.onResized();
        });
        
        this.animationTimer = new StarfieldAnimationTimer((deltaTime) -> this.tick(deltaTime));

        return this.container;
    }
    
    private void onResized() {
        this.width  = (int) this.container.getWidth();
        this.height = (int) this.container.getHeight();
        
        if (this.canvas != null) {
            this.container.getChildren().remove(this.canvas);
        }
        
        this.canvas = new Canvas(this.width, this.height);
        this.graphicsContext = canvas.getGraphicsContext2D();

        this.container.getChildren().add(this.canvas);
        
        this.refresh();
    }
           

    @Override
    public void refresh() {
    }

    /**
     * Enable animation.
     */
    public void enable() {
        this.animationTimer.start();
    }
    
    /**
     * Disable animation.
     */
    public void disable() {
        this.animationTimer.stop();
    }
    
    
    private void tick(double deltaTime) {
        for (Star star : stars) {
            if (star != null) {
                star.move(deltaTime, this.graphicsContext.getPixelWriter(), this.backgroundColor);
            }
        }
        boolean addMore = false;
        if (this.timeToNextStar > 0.0) {
            this.timeToNextStar -= deltaTime;
        } else {
            addMore = true;
            this.timeToNextStar = 0.1;
        }
        this.manageStars(addMore);
    }

    private void manageStars(boolean addMore) {
        for (int i = 0; i < stars.length; i++) {
            if (stars[i] == null) {
                if (addMore) {
                    addMore = false;
                    stars[i] = new Star(random, this.width, this.height, random.nextInt(Starfield.MAX_Z), this.direction);
                }
            } else {
                if (!stars[i].isAlive()) {
                    stars[i] = null;
                }
            }
        }
    }
}


/**
 * A single star.
 * 
 * @author Joni Yrjänä <joniyrjana@gmail.com>
 */
class Star {
    private double x;
    private double y;
    private final int    areaWidth;
    private final int    areaHeight;
    private final Color  color;
    private final double speed;
    private final Starfield.Direction direction;

    public Star(Random random, int areaWidth, int areaHeight, int z, Starfield.Direction direction) {
        this.areaWidth  = areaWidth;
        this.areaHeight = areaHeight;
        this.color      = this.generateColor(random, z);
        this.speed      = 600.0 / (double) z;
        this.direction  = direction;
        switch (direction) {
            default:
            case LEFT:
                this.x = areaWidth + 1;
                this.y = random.nextInt(areaHeight);
                break;
            case RIGHT:
                this.x = -1;
                this.y = random.nextInt(areaHeight);
                break;
            case UP:
                this.x = random.nextInt(areaWidth);
                this.y = areaHeight + 1;
                break;
            case DOWN:
                this.x = random.nextInt(areaWidth);
                this.y = -1;
                break;
        }
    }

    private Color generateColor(Random random, int z) {
        double brightness = 0.75 + 0.25 * random.nextDouble();
        if (z > 0) {
            brightness *= 1.0 - (double) z / (double) (Starfield.MAX_Z - 1);
        }
        double base = 0.9;
        double r = base + (1.0 - base) * random.nextDouble();
        double g = base + (1.0 - base) * random.nextDouble();
        double b = base + (1.0 - base) * random.nextDouble();
        return new Color(r * brightness, g * brightness, b * brightness, 1.0);
    }

    /**
     * Move this star based on settings and given delta time.
     * 
     * @param deltaTime       The amount of time passed since last move (in seconds)
     * @param pixelWriter     Pixelwriter object to use for drawing
     * @param backgroundColor The background color, used to remove this star from the old location
     */
    public void move(double deltaTime, PixelWriter pixelWriter, Color backgroundColor) {
        pixelWriter.setColor((int) this.x, (int) this.y, backgroundColor);
        double amount = speed * deltaTime;
        switch (this.direction) {
            case LEFT:
                this.x -= amount;
                break;
            case RIGHT:
                this.x += amount;
                break;
            case UP:
                this.y -= amount;
                break;
            case DOWN:
                this.y += amount;
                break;
        }
        pixelWriter.setColor((int) this.x, (int) this.y, this.color);
    }

    /**
     * Returns whether this star is still alive.
     * <p>
     * A star becomes dead once it reaches the opposite edge of the screen.
     * 
     * @return True if this star is still alive
     */
    public boolean isAlive() {
        switch (this.direction) {
            case LEFT:  return this.x >= -1;
            case RIGHT: return this.x <= this.areaWidth + 1;
            case UP:    return this.y >= -1;
            case DOWN:  return this.y <= this.areaHeight + 1;
            default:    return false;
        }
    }
}


interface StarfieldAnimationTicker {
    void tick(double deltaTime);
}


/**
 * Animation timer to handle the starfield animation.
 * 
 * @author Joni Yrjänä <joniyrjana@gmail.com>
 */
class StarfieldAnimationTimer extends AnimationTimer {
    private long lastUpdate;
    private final StarfieldAnimationTicker ticker;

    public StarfieldAnimationTimer(StarfieldAnimationTicker ticker) {
        this.ticker = ticker;
    }

    @Override
    public void start() {
        this.lastUpdate = System.nanoTime();
        super.start();
    }

    @Override
    public void handle(long now) {
        long elapsedNanoSeconds = now - this.lastUpdate;
        double elapsedSeconds = elapsedNanoSeconds / 1_000_000_000.0;
        this.ticker.tick(elapsedSeconds);
        this.lastUpdate = now;
    }
}
