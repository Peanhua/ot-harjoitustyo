/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.world.actions;

import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Item;

/**
 *
 * @author joyr
 */
public class ActionUse extends Action {
    private final GameObject item;

    public ActionUse(GameObject item) {
        super(Action.Type.USE);
        this.item = item;
    }

    @Override
    public void act(Character me) {
        if (this.item == null) {
            me.addMessage("You use nothing.");
            return;
        }
        
        if (this.item.getLocation().getContainerObject() != me) {
            me.addMessage("You don't have " + this.item.getName());
            return;
        }
        
        if (this.item instanceof Item) {
            ((Item) this.item).useItem(me, me);
        } else {
            me.addMessage("You don't know what to do with " + this.item.getName() + ".");
        }
    }
}
