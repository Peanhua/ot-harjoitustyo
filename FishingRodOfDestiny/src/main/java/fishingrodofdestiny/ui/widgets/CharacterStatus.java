/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.world.gameobjects.Character;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author joyr
 */
public class CharacterStatus extends Widget {
    
    private enum StatType {
        LEVEL, XP, HP, ATTACK, DEFENCE, CARRY, AC, DAMAGE, INVENTORY;
        
        public static String getName(StatType type) {
            switch (type) {
                case LEVEL:     return "Level";
                case XP:        return "XP";
                case HP:        return "HP";
                case ATTACK:    return "Attack";
                case DEFENCE:   return "Defence";
                case CARRY:     return "Carry";
                case AC:        return "AC";
                case DAMAGE:    return "Damage";
                case INVENTORY: return "Inventory";
                default:        throw new RuntimeException("Unknown type " + type + " for StatType.getName()");
            }
        }
    };

    private Character character;
    private List<Text> texts;
    

    public CharacterStatus(Character character) {
        this.character = character; // TODO: register listener for character to know when to do a refresh()
        this.texts = new ArrayList<>();
        for (StatType t : StatType.values()) {
            this.texts.add(null);
        }
    }

    
    @Override
    public Node createUserInterface() {
        GridPane grid = new GridPane();
        grid.setHgap(4);
        grid.setVgap(2);
        
        int row = 0;
        
        Text name = UserInterfaceFactory.createText(this.character.getName());
        GridPane.setConstraints(name, 0, row);
        GridPane.setColumnSpan(name, 3);
        grid.getChildren().add(name);
        row++;

        row = this.createStatControls(grid, row);

        this.refresh();
        
        return grid;
    }


    private int createStatControls(GridPane grid, int row) {
        for (StatType type : StatType.values()) {
            Text label = UserInterfaceFactory.createSmallText(StatType.getName(type) + ":");
            Text value = UserInterfaceFactory.createSmallText("");
            
            GridPane.setConstraints(label, 1, row);
            GridPane.setConstraints(value, 2, row);

            grid.getChildren().addAll(label, value);
            this.texts.set(type.ordinal(), value);
            
            row++;
        }
        return row;
    }
    
    
    @Override
    public void refresh() {
        for (StatType type : StatType.values()) {
            this.texts.get(type.ordinal()).setText(this.getValue(type));
        }
    }
    
    
    private String getValue(StatType type) {
        switch (type) {
            case LEVEL:     return "" + this.character.getLevel();
            case XP:        return "" + this.character.getExperiencePoints();
            case HP:        return "" + this.character.getHitpoints() + "/" + this.character.getMaxHitpoints();
            case ATTACK:    return "" + this.character.getAttack();
            case DEFENCE:   return "" + this.character.getDefence();
            case CARRY:     return "" + this.character.getInventory().getWeightLimit();
            case AC:        return "" + this.character.getArmorClass();
            case DAMAGE:    return "" + this.character.getDamage();
            case INVENTORY:
                int weight = this.character.getInventory().getWeight();
                int usage  = (int) (100.0 * (double) weight / (double) this.character.getInventory().getWeightLimit());
                return "" + weight + " (" + usage + "%)";
            default: throw new RuntimeException("Unkonwn StatType " + type + " for getValue()");
        }
    }
    
}
