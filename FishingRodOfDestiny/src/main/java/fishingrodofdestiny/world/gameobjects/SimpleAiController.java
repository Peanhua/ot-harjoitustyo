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
public class SimpleAiController extends Controller {

    public SimpleAiController(GameObject owner) {
        super(owner);
    }

    @Override
    public GameObject.Action getNextAction() {
        return null;
    }
    
}
