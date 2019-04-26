/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

import fishingrodofdestiny.world.Inventory;
import fishingrodofdestiny.observer.Observer;
import fishingrodofdestiny.observer.Subject;
import fishingrodofdestiny.world.GameObjectContainer;
import fishingrodofdestiny.world.TileGfx;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;


/**
 * The base class for all movable objects in the game.
 *
 * @author joyr
 */
public class GameObject implements GameObjectContainer {
    /**
     * The observers notified upon change to this GameObject.
     * Subclasses are expected to call onChange.notifyObservers() when changing their externally observable state.
     */
    protected Subject    onChange;
    
    private   String     objectType;
    private   String     name;
    private   boolean    isAlive;
    private   int        maxHitpoints;
    private   int        currentHitpoints;
    private   int        weight;
    private   Location   location;
    private   Inventory  inventory;
    private   Subject    onMessage;
    private   String     message;
    private   TileGfx    graphics;
    private   int        drawingOrder;
    private   boolean    canBePickedUp;
    private   Random     random;
    private   Double     timeToLive;

    /**
     * Create a new GameObject with default values.
     * 
     * @param objectType The type of this object.
     */
    public GameObject(String objectType) {
        this.objectType       = objectType;
        this.name             = objectType;
        this.isAlive          = true;
        this.maxHitpoints     = 1;
        this.currentHitpoints = 1;
        this.weight           = 1;
        this.inventory        = new Inventory();
        this.onChange         = new Subject();
        this.location         = new Location(this);
        this.onMessage        = new Subject();
        this.message          = "";
        this.graphics         = null;
        this.drawingOrder     = 0;
        this.canBePickedUp    = false;
        this.random           = new Random(); // TODO: maybe use a global random object?
        this.timeToLive       = null;
        
        this.inventory.listenOnChange(() -> {
            this.onChange.notifyObservers();
        });
    }

    /**
     * Return the type of this object, for example "player", or "apple".
     * <p>
     * While object types are unique, there can be multiple instances of each type in the game.
     * 
     * @return The type of this GameObject.
     */
    public final String getObjectType() {
        return this.objectType;
    }

    /**
     * This is used only for debugging purposes.
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GameObject(type=" + objectType + ",name='" + this.name + "')";
    }
    

    /**
     * Return a random number generator.
     * <p>
     * Each GameObject has its own unique Random generator.
     *
     * @return Random number generator.
     */
    public final Random getRandom() {
        return this.random;
    }
    

    /**
     * Return the drawing order value for this GameObject.
     * <p>
     * GameObjects with lower drawing order number are drawn first on screen, using <a href="https://en.wikipedia.org/wiki/Painter%27s_algorithm">painters algorithm</a> .
     *
     * @return The drawing order value between, default is 0.
     */
    public int getDrawingOrder() {
        return this.drawingOrder;
    }
    

    /**
     * Returns whether this GameObject can be picked up.
     *
     * @return True if this GameObject can be picked up.
     */
    public boolean getCanBePickedUp() {
        return this.canBePickedUp;
    }

    protected final void setCanBePickedUp(boolean canBePickedUp) {
        this.canBePickedUp = canBePickedUp;
    }
    
    
    protected final void setDrawingOrder(int order) {
        this.drawingOrder = order;
    }
    
    
    public final void setGraphics(TileGfx graphics) {
        this.graphics = graphics;
    }
        
    
    /**
     * Register an observer to be called whenever this GameObject is changed.
     * <p>
     * Will also be called when the inventory of this GameObject is changed.
     *
     * @param observer The observer object to be called upon change.
     */
    public void listenOnChange(Observer observer) {
        this.onChange.addObserver(observer);
    }
    
    /**
     * Register an observer to be called whenever this GameObject receives a new message.
     * <p>
     * Messages are short textual information meant to be displayed on screen.
     *
     * @param observer The observer object to be called when a new message arrives.
     */
    public void listenOnMessage(Observer observer) {
        this.onMessage.addObserver(observer);
    }

    /**
     * Send a new message to this GameObject.
     * Messages are short textual information meant to be displayed on screen.
     *
     * @param message The message string.
     */
    public void addMessage(String message) {
        if (this.message.length() > 0) {
            this.message += " ";
        }
        this.message += message;
        this.onMessage.notifyObservers();
    }

    /**
     * Returns the current messages added to this GameObject, and clears the messages.
     *
     * @return All the current messages sent to this GameObject.
     */
    public String popMessage() {
        String rv = this.message;
        this.message = "";
        return rv;
    }

    
    /**
     * Return the Location object.
     * <p>
     * There is a one-to-one mapping between Location and GameObject.
     * 
     * @return The Location object of this GameObject.
     */
    public final Location getLocation() {
        return this.location;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getHitpoints() {
        return this.currentHitpoints;
    }
    
    public final void setHitpoints(int hitpoints) {
        if (hitpoints < 1) {
            return;
        }
        this.currentHitpoints = Math.min(hitpoints, this.maxHitpoints);
        this.onChange.notifyObservers();
    }
    
    /**
     * Adjust the current hit points.
     * <p>
     * Adds the amount to the current hit points.
     * 
     * @param amount The amount to add to the current hit points.
     */
    public final void adjustHitpoints(int amount) {
        int hitpoints = this.currentHitpoints + amount;
        this.currentHitpoints = Math.min(hitpoints, this.maxHitpoints);
        if (this.currentHitpoints < 1) {
            this.destroy(null);
        }
        this.onChange.notifyObservers();
    }

    /**
     * Adjust the maximum hit points.
     * <p>
     * Adds the amount to the maximum hit points.
     * 
     * @param amount The amount to add to the maximum hit points.
     */
    public final void adjustMaxHitpoints(int amount) {
        this.maxHitpoints += amount;
        this.onChange.notifyObservers();
    }
    
    public int getMaxHitpoints() {
        return this.maxHitpoints;
    }

    /**
     * Return the capitalized name of this GameObject, ie. the first letter is changed to upper case.
     *
     * @return The capitalized name.
     */
    public final String getCapitalizedName() {
        if (this.getName() == null) {
            return "";
        }
        if (this.getName().length() == 0) {
            return this.getName();
        }
        String rv = this.getName().substring(0, 1).toUpperCase();
        if (this.getName().length() > 1) {
            rv += this.getName().substring(1);
        }
        return rv;
    }


    /**
     * Do damage to this GameObject.
     * <p>
     * Decreases the current hit points. If the hit points reach zero, the GameObject is destroyed.
     *
     * @see #destroy(GameObject)
     *
     * @param instigator The GameObject that causes the damage, can be null.
     * @param damage     The amount of hit points to decrease.
     */
    public final void hit(GameObject instigator, int damage) {
        this.onHit(instigator, damage);
        if (this.currentHitpoints > damage) {
            this.currentHitpoints -= damage;
            this.onChange.notifyObservers();
        } else {
            if (instigator != null) {
                instigator.addMessage(this.getCapitalizedName() + " is destroyed.");
            }
            this.currentHitpoints = 0;
            this.destroy(instigator);
            // onChange.notifyObservers() is not called here because destroy() does that for us
            // TODO: make it "safe" to call notifyObservers() multiple times (asynchronously) within a timeframe so that the observers get notified only once
        }
    }
    
    /**
     * Called when this object is getting hit.
     * 
     * @param instigator The GameObject that causes the damage, can be null.
     * @param damage     The amount of hit points decreased.
     */
    public void onHit(GameObject instigator, int damage) {
    }
    
    /**
     * Returns the damage caused by falling.
     * 
     * @param height From how high the object falls, in meters.
     * @return The amount of damage done by the fall.
     */
    public int getFallDamage(int height) {
        return 1;
    }

    /**
     * Destroy this GameObject.
     * <p>
     * This GameObject is removed from its container.
     * Calls onDestroyed() of this object, and onDestroyTarget() of instigator object.
     *
     * @see #onDestroyed(GameObject)
     * @see #onDestroyTarget(GameObject)
     *
     * @param instigator The GameObject who is responsible of destroying this GameObject, can be null.
     */
    public final void destroy(GameObject instigator) {
        this.onDestroyed(instigator);
        if (instigator != null) {
            instigator.onDestroyTarget(this);
        }
        this.isAlive = false;
        this.getLocation().moveTo((Tile) null);
        this.onChange.notifyObservers();
    }
    
    /**
     * This is called by destroy() when this GameObject is destroyed.
     * 
     * @see #destroy(GameObject)
     * @param instigator The GameObject responsible of destroying this GameObject, can be null.
     */
    protected void onDestroyed(GameObject instigator) {
        // Drop inventory:
        List<GameObject> items = new ArrayList<>(this.getInventory().getObjects());
        // TODO: if Location.moveTo() had a variant moveTo(Location) then this could be one line
        Tile myTile = this.getLocation().getContainerTile();
        if (myTile != null) {
            items.forEach(obj -> obj.getLocation().moveTo(myTile));
        } else {
            GameObject myContainer = this.getLocation().getContainerObject();
            if (myContainer != null) {
                items.forEach(obj -> obj.getLocation().moveTo(myContainer));
            }
        }
    }

    /**
     * Return whether this is alive or not.
     * <p>
     * The GameObject becomes not alive when it is destroyed via the destroy() method call.
     * 
     * @return True if this GameObject is alive.
     */
    public final boolean isAlive() {
        return this.isAlive;
    }
    
    /**
     * This is called when this GameObject destroys the given target.
     *
     * @param target The GameObject that was destroyed.
     */
    public void onDestroyTarget(GameObject target) {
    }

    /**
     * Set the maximum number of hit points, also sets the current hit points to the maximum.
     * 
     * @param amount The new maximum number of hit points.
     */    
    public final void setMaxHitpoints(int amount) {
        if (amount <= 0) {
            throw new RuntimeException("Amount must be greater than 0.");
        }
        this.currentHitpoints = amount;
        this.maxHitpoints     = amount;
    }
    
    
    public final Inventory getInventory() {
        return this.inventory;
    }
    

    /**
     * Return the total weight of this GameObject, including the weight of the inventory.
     *
     * @return The total weight.
     */
    public final int getWeight() {
        return this.weight + this.inventory.getWeight();
    }

    /**
     * Set this GameObjects weight.
     *
     * @param weight The new weight value.
     */
    public final void setWeight(int weight) {
        this.weight = weight;
        this.onChange.notifyObservers();
    }
    
    
    /**
     * Return a list of valid targets to attack at, the targets are searched from the given tile.
     *
     * @param tile The tile to search for the targets.
     *
     * @return List of GameObjects that this GameObject deem as valid targets for attacking.
     */
    public List<GameObject> getValidAttackTargets(Tile tile) {
        return null;
    }
    
    /**
     * Draw this GameObjects visual representation into the given context.
     *
     * @param context The target GraphicsContext object to draw onto.
     * @param x       The target X coordinate.
     * @param y       The target Y coordinate.
     * @param size    The target size (width and height).
     */
    public void draw(GraphicsContext context, int x, int y, int size) {
        this.graphics.draw(context, x, y, size);
    }
    
    /**
     * Set the time (in seconds) how long this object will stay alive.
     * <p>
     * After the time has passed, destroy() is called on this object.
     * If the timeToLive is null, then this object will not automatically decay.
     * 
     * @param timeToLive The time to live in seconds, null disables.
     */
    public void setTimeToLive(Double timeToLive) {
        this.timeToLive = timeToLive;
    }
    
    /**
     * This method is called periodically while this GameObject is alive.
     * 
     * @param deltaTime The time (in seconds) since last call.
     */
    public void tick(double deltaTime) {
        if (this.timeToLive != null) {
            this.timeToLive -= deltaTime;
            if (this.timeToLive <= 0.0) {
                this.destroy(null);
            }
        }
    }

    // Implement GameObjectContainer:
    @Override
    public List<GameObject> getObjects(String objectType) {
        return this.getInventory().getObjects(objectType);
    }

    @Override
    public int getObjectCount(String objectType) {
        return this.getInventory().getObjectCount(objectType);
    }
}
