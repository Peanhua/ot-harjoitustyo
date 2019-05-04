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
package fishingrodofdestiny.world.controllers;

import fishingrodofdestiny.world.Level;
import fishingrodofdestiny.world.actions.ActionAttack;
import fishingrodofdestiny.world.actions.Action;
import fishingrodofdestiny.world.actions.ActionMove;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;

/**
 *
 * @author joyr
 */
public class SimpleAiController extends Controller {
    private boolean isAggressive;
    private int     automaticAggressionDistance; // How close enemies must be for this getting (sometimes) automatically aggressive.

    public SimpleAiController(Character owner) {
        super(owner);
        this.isAggressive = false;
        this.automaticAggressionDistance = 2;
    }
    
    public void setAggressive(boolean aggressive) {
        this.isAggressive = aggressive;
    }
    
    public boolean isAggressive() {
        return this.isAggressive;
    }
    

    @Override
    public Action getNextAction() {
        Action action = super.getNextAction();
        if (action == null) {
            action = this.tryToAttack();
        }
        if (action == null) {
            if (this.isAggressive) {
                action = this.tryToFindPlayer();
            }
        }
        if (action == null) {
            this.makeAggressive();
        }
        
        return action;
    }

    private void makeAggressive() {
        if (this.isAggressive) {
            return;
        }
        if (this.getOwner().getRandom().nextInt(100) < 30) {
            int distance = this.getDistanceToPlayer();
            if (distance >= 0 && distance <= this.automaticAggressionDistance) {
                this.isAggressive = true;
            }
        }
    }
    
    
    private int getDistanceToPlayer() {
        Tile myTile = this.getOwner().getLocation().getContainerTile();
        if (myTile == null) {
            return -1;
        }
        Level level = myTile.getLevel();
        List<GameObject> players = level.getObjects("player");
        for (GameObject player : players) {
            Tile tile = player.getLocation().getContainerTile();
            int deltaX = tile.getX() - myTile.getX();
            int deltaY = tile.getY() - myTile.getY();
            return (int) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        }
        return -1;
    }
    
    
    protected Action tryToAttack() {
        Tile tile = this.getOwner().getLocation().getContainerTile();
        List<GameObject> targets = this.getOwner().getValidAttackTargets(tile);
        if (targets == null) {
            return null;
        }
        return new ActionAttack(targets.get(0));
    }
    
    protected Action tryToFindPlayer() {
        Tile myTile = this.getOwner().getLocation().getContainerTile();
        if (myTile == null) {
            return null;
        }
        Level level = myTile.getLevel();
        List<GameObject> players = level.getObjects("player"); // TODO: this is not using the valid target matching system, fix it
        for (GameObject player : players) {
            Tile tile = player.getLocation().getContainerTile();
            int deltaX = tile.getX() - myTile.getX();
            int deltaY = tile.getY() - myTile.getY();
            int maxDistance = 7;
            if (Math.abs(deltaX) <= maxDistance || Math.abs(deltaY) <= maxDistance) {
                return this.moveTowards(deltaX, deltaY);
            }
        }
        
        return null;
    }
    
    protected Action moveTowards(int deltaX, int deltaY) {
        int dx = 0;
        int dy = 0;
        if (deltaX != 0) {
            dx = deltaX / Math.abs(deltaX);
        }
        if (deltaY != 0) {
            dy = deltaY / Math.abs(deltaY);
        }
        if (dx != 0 && dy != 0) {
            if (this.getOwner().getRandom().nextInt(100) < 50) {
                dx = 0;
            } else {
                dy = 0;
            }
        }
        return new ActionMove(dx, dy);
    }
}
