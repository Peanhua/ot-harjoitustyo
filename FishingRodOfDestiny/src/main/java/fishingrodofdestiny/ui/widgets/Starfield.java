/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private final static int MAX_Z = 30;
    
    private class Star {
        private double x;
        private final double y;
        private final Color  color;
        private final double speed;
                
        public Star(Random random, int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.color = this.generateColor(random, z);
            this.speed = 600.0 / (double) z;
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
        
        public void move(double deltaTime, PixelWriter pixelWriter, Color backgroundColor) {
            pixelWriter.setColor((int) this.x, (int) this.y, backgroundColor);
            this.x -= speed * deltaTime;
            pixelWriter.setColor((int) this.x, (int) this.y, this.color);
        }
        
        public boolean isAlive() {
            return this.x >= 0.0;
        }
    }

    public interface StarfieldAnimationTicker {
        void tick(double deltaTime);
    }

    private class StarfieldAnimationTimer extends AnimationTimer {
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

    
    public Starfield() {
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

    public void enable() {
        this.animationTimer.start();
    }
    
    public void disable() {
        this.animationTimer.stop();
    }
    
    
    private void tick(double deltaTime) {
        for (Star star : stars) {
            if (star != null) {
                star.move(deltaTime, this.graphicsContext.getPixelWriter(), Color.BLACK);
            }
        }
        boolean addMore = false;
        if (this.timeToNextStar > 0.0) {
            this.timeToNextStar -= deltaTime;
        } else {
            addMore = true;
            this.timeToNextStar = 0.1;
        }
            
        for (int i = 0; i < stars.length; i++) {
            if (stars[i] == null) {
                if (addMore) {
                    addMore = false;
                    stars[i] = new Star(random, this.width + 1, random.nextInt(this.height), random.nextInt(Starfield.MAX_Z));
                }
            } else {
                if (!stars[i].isAlive()) {
                    stars[i] = null;
                }
            }
        }
    }
}
