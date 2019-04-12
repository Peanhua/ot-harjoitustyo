/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world;

import fishingrodofdestiny.world.gameobjects.NonPlayerCharacter;
import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.WallTile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Based on http://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation
 * 
 * @author joyr
 */
public class BSPLevelGenerator extends LevelGenerator {
    
    public BSPLevelGenerator(Random random, int width, int height) {
        super(random, width, height);
    }

    
    @Override
    public Level generateLevel(int caveLevel) {
        LevelSettings settings = new LevelSettings();
        settings.addEnemyType(NonPlayerCharacter.class, 10 + caveLevel * 5, 1.0);
        
        Level level = new Level(settings, this.width, this.height);
        
        Node root = new Node(this.random, 12, null, 1, 1, this.width - 2, this.height - 2);
        root.split(false);
        root.setupRooms();
        root.fillInRooms(level);
        root.fillInCorridors(level);
        this.fillEmptySpace(level);
        
        return level;
    }
}



class Room {
    int topleftX;
    int topleftY;
    int width;
    int height;

    public Room(int x, int y, int width, int height) {
        this.topleftX = x;
        this.topleftY = y;
        this.width    = width;
        this.height   = height;
    }

    public void fillInLevel(Level level) {
        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int xx = this.topleftX + x;
                int yy = this.topleftY + y;
                level.setTile(xx, yy, new FloorTile(level, xx, yy));
            }
        }
    }

    @Override
    public String toString() {
        return "Room(@" + this.topleftX + "," + this.topleftY + " size=" + this.width + "x" + this.height + ")";
    }
}


class Position {
    public int x;
    public int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Position(Random random, Node node) {
        Room room = node.getRoom();
        if (room != null) {
            this.x = room.topleftX + random.nextInt(room.width);
            this.y = room.topleftY + random.nextInt(room.height);
        } else {
            this.x = node.getTopLeft().x + random.nextInt(node.getWidth());
            this.y = node.getTopLeft().y + random.nextInt(node.getHeight());
        }
    }
}


class Node {
    private Random random;
    private int minSize;

    private Node parent;
    private Node childA;
    private Node childB;

    private int topleftX;
    private int topleftY;
    private int width;
    private int height;

    private Room room;

    public Node(Random random, int minSize, Node parent, int topleftX, int topleftY, int width, int height) {
        this.random   = random;
        this.minSize  = minSize;
        this.parent   = parent;
        this.childA   = null;
        this.childB   = null;
        this.topleftX = topleftX;
        this.topleftY = topleftY;
        this.width    = width;
        this.height   = height;
        this.room     = null;
    }
    
    public Room getRoom() {
        return this.room;
    }
    
    public Position getTopLeft() {
        return new Position(this.topleftX, this.topleftY);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }

    public void split(boolean horizontal) {
        if (this.canBeSplit(horizontal)) {
            if (horizontal) {
                int splittingPos = this.minSize / 2 + this.random.nextInt(this.width - this.minSize + 1);
                this.childA = new Node(this.random, this.minSize, this, this.topleftX,                this.topleftY, splittingPos,              this.height);
                this.childB = new Node(this.random, this.minSize, this, this.topleftX + splittingPos, this.topleftY, this.width - splittingPos, this.height);
            } else {
                int splittingPos = this.minSize / 2 + this.random.nextInt(this.height - this.minSize + 1);
                this.childA = new Node(this.random, this.minSize, this, this.topleftX, this.topleftY,                this.width, splittingPos);
                this.childB = new Node(this.random, this.minSize, this, this.topleftX, this.topleftY + splittingPos, this.width, this.height - splittingPos);
            }

            if (this.random.nextInt(100) < 80) {
                horizontal = !horizontal;
            }
            this.childA.split(horizontal);
            this.childB.split(horizontal);
        }
    }

    private boolean canBeSplit(boolean horizontal) {
        if (horizontal) {
            return this.width >= this.minSize;
        } else {
            return this.height >= this.minSize;
        }
    }


    public void setupRooms() {
        if (this.childA != null) {
            this.childA.setupRooms();
        }
        if (this.childB != null) {
            this.childB.setupRooms();
        }

        if (this.childA == null && this.childB == null) {
            int w = 1 + this.random.nextInt(this.width - 1);
            int h = 1 + this.random.nextInt(this.height - 1);
            int x = this.topleftX + this.random.nextInt(this.width - w);
            int y = this.topleftY + this.random.nextInt(this.height - h);
            this.room = new Room(x, y, w, h);
        }
    }


    public void fillInRooms(Level level) {
        if (this.childA != null) {
            this.childA.fillInRooms(level);
        }
        if (this.childB != null) {
            this.childB.fillInRooms(level);
        }
        if (this.room != null) {
            this.room.fillInLevel(level);
        }
    }


    public void fillInCorridors(Level level) {
        if (this.childA != null) {
            this.childA.fillInCorridors(level);
        }
        if (this.childB != null) {
            this.childB.fillInCorridors(level);
        }
        if (this.childA != null && this.childB != null) {
            this.createCorridor(level, this.childA, this.childB);
        }
    }
    

    private void createCorridor(Level level, Node from, Node to) {
        Position src = new Position(this.random, from);
        Position dst = new Position(this.random, to);

        while (true) {
            Tile t = level.getTile(src.x, src.y);
            if (t == null) {
                level.setTile(src.x, src.y, new FloorTile(level, src.x, src.y));
            }

            if (src.x == dst.x && src.y == dst.y) {
                break;
            }

            int deltaX = dst.x - src.x;
            int deltaY = dst.y - src.y;
            if (deltaX != 0 && (deltaY == 0 || this.random.nextInt(100) < 75)) {
                src.x += deltaX / Math.abs(deltaX);
            } else {
                src.y += deltaY / Math.abs(deltaY);
            }
        }
    }


    public void dump() {
        if (this.childA != null) {
            this.childA.dump();
        }
        if (this.childB != null) {
            this.childB.dump();
        }

        if (this.childA == null && this.childB == null) {
            System.out.println(this);
        }            
    }

    @Override
    public String toString() {
        return "Node(topleft=" + this.topleftX + "," + this.topleftY + ": size=" + this.width + "," + this.height + ": room=" + this.room + ")";
    }
}
