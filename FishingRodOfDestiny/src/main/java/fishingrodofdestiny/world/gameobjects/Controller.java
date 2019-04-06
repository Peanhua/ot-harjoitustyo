/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.gameobjects;

/**
 *
 * @author joyr
 */
public abstract class Controller {
    
    private GameObject owner;
    
    public Controller(GameObject owner) {
        this.owner = owner;
    }
    
    public GameObject getOwner() {
        return this.owner;
    }
    
    public abstract GameObject.Action getNextAction();
}
