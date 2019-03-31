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
public class Character extends GameObject {
    
    private int attack;
    private int defence;
    private int level;
    private int experiencePoints;

    public Character() {
        super();
        this.attack           = 0;
        this.defence          = 0;
        this.level            = 0;
        this.experiencePoints = 0;
    }
    
    @Override
    public String toString() {
        return "Character(" + super.toString()
                + ",attack=" + this.attack
                + ",defence=" + this.defence
                + ",level=" + this.level
                + ",xp=" + this.experiencePoints
                + ")";
    }
    
    public int getAttack() {
        return this.attack;
    }
    
    public int getDefence() {
        return this.defence;
    }
    
    public void adjustAttack(int amount) {
        this.attack += amount;
        if(this.attack < 0)
            this.attack = 0;
    }
    
    public void adjustDefence(int amount) {
        this.defence += amount;
        if(this.defence < 0)
            this.defence = 0;
    }
}
