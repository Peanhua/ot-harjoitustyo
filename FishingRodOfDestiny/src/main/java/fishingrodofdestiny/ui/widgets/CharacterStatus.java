/*
 * Copyright (C) 2019 Joni Yrjänä <joniyrjana@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.world.gameobjects.Armor;
import fishingrodofdestiny.world.gameobjects.Buff;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.gameobjects.GameObject;
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
        LEVEL, XP, NEXTLEVELXP, HP, ATTACK, DEFENCE, CARRY, AC, DAMAGE, INVENTORY, BUFFS, ACTIONS;
        
        public static String getName(StatType type) {
            switch (type) {
                case LEVEL:       return "Level";
                case XP:          return "XP";
                case NEXTLEVELXP: return "Next level";
                case HP:          return "HP";
                case ATTACK:      return "Attack";
                case DEFENCE:     return "Defence";
                case CARRY:       return "Carry";
                case AC:          return "AC";
                case DAMAGE:      return "Damage";
                case INVENTORY:   return "Inventory";
                case BUFFS:       return "Buffs";
                case ACTIONS:     return "Actions";
                default:          throw new RuntimeException("Unknown type " + type + " for StatType.getName()");
            }
        }
    };

    private Character  character;
    private List<Text> texts;
    private Text       weaponText;
    private List<Text> armorTexts;

    public CharacterStatus(Character character) {
        this.character = character;
        this.texts = new ArrayList<>();
        for (StatType t : StatType.values()) {
            this.texts.add(null);
        }
        this.armorTexts = new ArrayList<>();
        for (int i = 0; i < Armor.Slot.values().length; i++) {
            this.armorTexts.add(null);
        }
        
        this.character.listenOnChange(() -> {
            this.refresh();
        });
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
        row = this.createWeaponControls(grid, row);
        row = this.createArmorControls(grid, row);
        
        this.refresh();
        
        return grid;
    }


    private int createStatControls(GridPane grid, int row) {
        for (StatType type : StatType.values()) {
            Text label = UserInterfaceFactory.createSmallText(StatType.getName(type) + ":");
            Text value = UserInterfaceFactory.createSmallText("");
            
            GridPane.setConstraints(label, 1, row);
            GridPane.setConstraints(value, 2, row);
            value.setWrappingWidth(100);

            grid.getChildren().addAll(label, value);
            this.texts.set(type.ordinal(), value);
            
            row++;
        }
        return row;
    }
    

    private int createWeaponControls(GridPane grid, int row) {
        Text label = UserInterfaceFactory.createSmallText("Weapon:");
        this.weaponText = UserInterfaceFactory.createSmallText("");

        GridPane.setConstraints(label, 1, row);
        row++;
        GridPane.setConstraints(this.weaponText, 1, row);
        GridPane.setColumnSpan(this.weaponText, 2);
        row++;

        grid.getChildren().addAll(label, this.weaponText);
        
        return row;
    }
    
    
    private int createArmorControls(GridPane grid, int row) {
        for (Armor.Slot slot : Armor.Slot.values()) {
            Text label = UserInterfaceFactory.createSmallText(slot.getDisplayName() + ":");
            Text value = UserInterfaceFactory.createSmallText("");
            this.armorTexts.set(slot.ordinal(), value);

            GridPane.setConstraints(label, 1, row);
            row++;
            GridPane.setConstraints(value, 1, row);
            GridPane.setColumnSpan(value, 2);
            row++;

            grid.getChildren().addAll(label, value);
        }
        return row;
    }
    
    
    @Override
    public void refresh() {
        for (StatType type : StatType.values()) {
            this.texts.get(type.ordinal()).setText(this.getValue(type));
        }
        
        GameObject weapon = this.character.getWeapon();
        if (weapon != null) {
            this.weaponText.setText(" " + weapon.getName());
        } else {
            this.weaponText.setText("");
        }
        
        for (Armor.Slot slot : Armor.Slot.values()) {
            Armor armor = this.character.getArmor(slot);
            if (armor != null) {
                this.armorTexts.get(slot.ordinal()).setText(" " + armor.getName());
            } else {
                this.armorTexts.get(slot.ordinal()).setText("");
            }
        }
    }
    
    
    private String getValue(StatType type) {
        switch (type) {
            case LEVEL:       return "" + this.character.getCharacterLevel();
            case XP:          return "" + this.character.getExperiencePoints();
            case NEXTLEVELXP: return "" + this.character.getExperiencePointsForCharacterLevel(this.character.getCharacterLevel() + 1);
            case HP:          return "" + this.character.getHitpoints() + "/" + this.character.getMaxHitpoints();
            case ATTACK:      return "" + this.character.getAttack();
            case DEFENCE:     return "" + this.character.getDefence();
            case CARRY:       return "" + this.character.getCarryingCapacity();
            case AC:          return "" + this.character.getArmorClass();
            case DAMAGE:      return "" + this.character.getDamage();
            case INVENTORY:   return this.getValueForInventory();
            case ACTIONS:     return "" + this.character.getActionsTaken();
            case BUFFS:       return this.getValueForBuffs();
            default: throw new RuntimeException("Unkonwn StatType " + type + " for getValue()");
        }
    }
    
    private String getValueForInventory() {
        int weight = this.character.getInventory().getWeight();
        int usage  = (int) (100.0 * (double) weight / (double) this.character.getCarryingCapacity());
        return "" + weight + " (" + usage + "%)";
    }
    
    private String getValueForBuffs() {
        String buffs = this.character.getBuffs().stream().map(b -> b.getName()).reduce(null, (a, b) -> (a != null ? a + ", " : "") + b);
        if (buffs == null) {
            buffs = "";
        }
        return buffs;
    }
    
}
