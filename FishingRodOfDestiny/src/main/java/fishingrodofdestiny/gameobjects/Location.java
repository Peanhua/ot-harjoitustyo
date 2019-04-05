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
    private GameObject me;
    private Inventory  container;
    private GameObject containerObject;
    private Tile       containerTile;
    
    public Location(GameObject owner) {
        this.me              = owner;
        this.container       = null;
        this.containerObject = null;
        this.containerTile   = null;
    }

    
    private void removeFromContainer() {
        if(this.container != null)
            this.container.remove(this.me);
    }
    

    private void moveTo(Inventory target) {
        if(this.container != target) {
            this.removeFromContainer();
            this.container = target;
            this.container.add(me);
        }
    }

    
    public void moveTo(Tile target) {
        this.containerObject = null;
        this.containerTile   = target;
        if(target != null) {
            Inventory ti = target.getInventory();
            this.moveTo(ti);
        } else {
            this.removeFromContainer();
        }
    }
    
    public void moveTo(GameObject target) {
        this.containerObject = target;
        this.containerTile   = null;
        if(target != null) {
            Inventory ti = target.getInventory();
            this.moveTo(ti);
        } else {
            this.removeFromContainer();
        }
    }
    

    public Inventory getContainerInventory() {
        return this.container;
    }
    

    public Tile getContainerTile() {
        return this.containerTile;
    }
    

    public GameObject getContainerObject() {
        return this.containerObject;
    }
}
