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
public class Game {
    private Player player;
    
    public Game(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
