/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.gameobjects;

/**
 *
 * @author joyr
 */
public class Player extends Character {
    public Player() {
        super();
    }
    
    @Override
    public String toString() {
        return "Player(" + super.toString()
                + ")";
    }
}
