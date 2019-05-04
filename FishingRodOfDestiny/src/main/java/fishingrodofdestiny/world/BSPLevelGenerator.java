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

import fishingrodofdestiny.world.tiles.FloorTile;
import fishingrodofdestiny.world.tiles.Tile;
import fishingrodofdestiny.world.tiles.WallTile;
import java.util.List;
import java.util.Random;

/**
 * Based on http://www.roguebasin.com/index.php?title=Basic_BSP_Dungeon_generation
 * 
 * @author joyr
 */
public class BSPLevelGenerator extends LevelGenerator {
    
    public BSPLevelGenerator(CaveSettings settings) {
        super(settings);
    }

    
    @Override
    public Level generateLevel(int caveLevel) {
        final int width  = this.getSettings().getLevelWidth(caveLevel);
        final int height = this.getSettings().getLevelHeight(caveLevel);
        
        final Level level = new Level(this.getSettings().getEnemySpawner(caveLevel), caveLevel, width, height);
        
        final Node root = new Node(this.getSettings().getRandom(), 12, null, 1, 1, width - 2, height - 2);
        root.split(false);
        root.setupRooms();
        root.fillInRooms(level);
        root.fillInCorridors(level);
        this.fillEmptySpace(level);
        
        return level;
    }
    

    @Override
    public void connectStartEnd(Level level) {
        LevelMapConnectedTilesAlgorithm cta = new LevelMapConnectedTilesAlgorithm(level.getMap());
        List<List<Tile>> connectedTileGroups = cta.getConnectedTileGroups();

        Tile stairsUp   = level.getStairsUp().get(0);
        Tile stairsDown = level.getStairsDown().get(0);
        List<Tile> startGroup = null;
        List<Tile> endGroup   = null;
        for (List<Tile> list : connectedTileGroups) {
            if (startGroup == null && list.contains(stairsUp)) {
                startGroup = list;
            }
            if (endGroup == null && list.contains(stairsDown)) {
                endGroup = list;
            }
        }
        if (startGroup != endGroup) {
            this.connectTileGroups(level, startGroup, endGroup);
        }
    }

    private void connectTileGroups(Level level, List<Tile> srcGroup, List<Tile> dstGroup) {
        Tile srcClosest = null;
        Tile dstClosest = null;
        long closestDistance = 0;
        
        for (Tile src : srcGroup) {
            for (Tile dst : dstGroup) {
                long dx = src.getX() - dst.getX();
                long dy = src.getY() - dst.getY();
                long distance = dx * dx + dy * dy;
                if (srcClosest == null || distance < closestDistance) {
                    srcClosest = src;
                    dstClosest = dst;
                    closestDistance = distance;
                }
            }
        }
        
        Node startNode = new Node(this.getSettings().getRandom(), 1, null, srcClosest.getX(), srcClosest.getY(), 1, 1);
        Node endNode   = new Node(this.getSettings().getRandom(), 1, null, dstClosest.getX(), dstClosest.getY(), 1, 1);
        startNode.createCorridor(level, endNode);
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

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.x != other.x || this.y != other.y) {
            return false;
        }
        return true;
    }
    

    @Override
    public int hashCode() {
        String h = "" + this.x + "," + this.y;
        return h.hashCode();
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
            this.childA.createCorridor(level, this.childB);
        }
    }
    

    public void createCorridor(Level level, Node to) {
        Position src = new Position(this.random, this);
        Position dst = new Position(this.random, to);

        while (true) {
            Tile t = level.getTile(src.x, src.y);
            if (t == null || t.getClass() == WallTile.class) {
                level.setTile(src.x, src.y, new FloorTile(level, src.x, src.y));
            }
            
            if (src.equals(dst)) {
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
}
