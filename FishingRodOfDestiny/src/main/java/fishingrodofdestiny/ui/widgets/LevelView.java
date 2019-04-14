/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.gameobjects.Player;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author joyr
 */
public class LevelView extends Widget {
    private Pane            container;
    private Canvas          canvas;
    private GraphicsContext graphicsContext;
    private int             width;
    private int             height;
    private Level           level;
    private int             centerAtX;
    private int             centerAtY;
    private int             tileSize;
    private Player          player;
    
    public LevelView(Player player) {
        this.level    = null;
        this.tileSize = 32;
        this.player   = player;
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
        
        this.container.heightProperty().addListener((o) -> {
            this.onResized();
        });

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
        if (this.width == 0 || this.canvas == null) {
            return;
        }

        this.graphicsContext.setFill(Color.BLACK);
        this.graphicsContext.fillRect(0, 0, this.width, this.height);
        // TODO: check if clearRect() would be faster
        
        if (this.level != null) {
            int horizontalTiles = this.width / this.tileSize;
            int verticalTiles   = this.height / this.tileSize;
            int maxX = this.level.getWidth() - horizontalTiles;
            int maxY = this.level.getHeight() - verticalTiles;
            int topLeftX = Math.max(0, Math.min(maxX, this.centerAtX - horizontalTiles / 2));
            int topLeftY = Math.max(0, Math.min(maxY, this.centerAtY - verticalTiles / 2));
            this.level.draw(this.player.getLevelMemory(this.level), this.graphicsContext, this.tileSize, topLeftX, topLeftY, horizontalTiles, verticalTiles);
        }
    }
    
    
    public void setLevel(Level level) {
        this.level = level;
    }
    
    
    public void centerAtTile(int x, int y) {
        this.centerAtX = x;
        this.centerAtY = y;
    }
    
    
    public void setTileSize(int size) {
        if (size > 4 && size <= 64) {
            this.tileSize = size;
        }
    }
    
    public int getTileSize() {
        return this.tileSize;
    }
}
