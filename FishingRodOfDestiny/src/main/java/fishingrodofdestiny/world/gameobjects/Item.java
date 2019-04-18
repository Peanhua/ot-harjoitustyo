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
public class Item extends GameObject {
    public Item(String name) {
        super(name);
        this.setCanBePickedUp(true);
    }
    
    @Override
    public int getFallDamage(int height) {
        return 0;
    }
}
