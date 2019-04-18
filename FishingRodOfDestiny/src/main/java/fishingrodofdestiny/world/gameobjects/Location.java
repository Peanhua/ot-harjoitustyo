/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.world.tiles.Tile;

/**
 * Location of a GameObject, can be either a location on a tile in a cave level, or in the inventory of another GameObject.
 * <p>
 * GameObject knows where it's located, and the container (Tile or another GameObject) also knows what GameObjects are located in it.
 *
 * There is a one-to-one mapping between Location and GameObject. This is not enforced, and it's enforcement is the responsibility of GameObject.
 *
 * Does not detect circular situation where object A is inside object B, and object B is inside object A.
 *
 * @author joyr
 */
public class Location {
    private final GameObject me;
    private final Subject    onChange;
    private Inventory        container;
    private GameObject       containerObject;
    private Tile             containerTile;

    /**
     * Create a new Location tied to the given owner GameObject.
     * This should normally be only called from the owning GameObject.
     *
     * @param owner The owner of this location object.
     */    
    public Location(GameObject owner) {
        this.me              = owner;
        this.container       = null;
        this.containerObject = null;
        this.containerTile   = null;
        this.onChange        = new Subject();
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
        final Location other = (Location) obj;

        return this.container == other.container;
    }

    @Override
    public int hashCode() {
        if (this.container == null) {
            return 42;
        }
        return this.container.hashCode();
    }
    

    /**
     * Register an observer to be called whenever this location is changed,
     * ie. when the owner of this location is moved.
     *
     * @param observer The observer object to be called upon change.
     */
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    

    private void moveToInventory(Inventory target) {
        if (this.container != target) {
            if (this.container != null) {
                this.container.remove(this.me);
            }
            this.container = target;
            if (this.container != null) {
                this.container.add(me);
            }
            this.onChange.notifyObservers();
        }
    }


    /**
     * Change the location to be inside a tile object.
     *
     * @param target The destination tile object.
     */
    public void moveTo(Tile target) {
        this.containerObject = null;
        this.containerTile   = target;
        if (target != null) {
            Inventory ti = target.getInventory();
            this.moveToInventory(ti);
            target.onEnter(this.me);
        } else {
            this.moveToInventory(null);
        }
    }

    /**
     * Change the location to be inside another GameObject.
     * For example: players inventory.
     *
     * @param target The destination GameObject.
     */
    public void moveTo(GameObject target) {
        this.containerObject = target;
        this.containerTile   = null;
        if (target != null) {
            Inventory ti = target.getInventory();
            this.moveToInventory(ti);
        } else {
            this.moveToInventory(null);
        }
    }
    
    /**
     * Return the inventory where this location is located in.
     *
     * @return The inventory object, or null if this location is not inside anything.
     */
    public Inventory getContainerInventory() {
        return this.container;
    }
    

    /**
     * Return the tile object this location is located in.
     *
     * @return The tile object, or null if this location is not inside a tile.
     */
    public Tile getContainerTile() {
        return this.containerTile;
    }
    
    /**
     * Return the GameObject this location is located in.
     *
     * @return The GameObject, or null if this location is not inside a GameObject.
     */
    public GameObject getContainerObject() {
        return this.containerObject;
    }
}
