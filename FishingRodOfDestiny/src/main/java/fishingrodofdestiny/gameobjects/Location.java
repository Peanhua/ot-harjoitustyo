/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.gameobjects;

import fishingrodofdestiny.world.Tile;

/**
 * Location of a GameObject, can be either a location on a tile in a cave level, or in the inventory of another GameObject.
 * GameObject knows where it's located, and the container (Tile or another GameObject) also knows what GameObjects are located in it.
 *
 * @author joyr
 */
public class Location {
    private Tile       containerTile;
    private GameObject containerInventory;
    
    public Location() {
        this.containerTile      = null;
        this.containerInventory = null;
    }
    
    private void removeFromContainer() {
        if(this.containerTile != null) {
            this.containerTile = null;
            
        } else if(this.containerInventory != null) {
            this.containerInventory = null;
        }
    }
    
    public void set(Tile tile) {
        if(this.containerTile != tile) {
            this.removeFromContainer();
            this.containerTile = tile;
        }
    }
    
    public void set(GameObject target) {
        if(this.containerInventory != target) {
            this.removeFromContainer();
            this.containerInventory = target;
        }
    }
    
    public Tile getContainerTile() {
        return this.containerTile;
    }
    
    public GameObject getContainerInventory() {
        return this.containerInventory;
    }
}
