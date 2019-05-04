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

import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.gameobjects.Character;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Shows information about the given location (tile) on the cave level.
 * 
 * @author joyr
 */
public class LocationInfo extends Widget {
    
    private Tile       currentTile;
    private Text       tileName;
    private Text       characterCount;
    private Text       characterList;
    private Text       itemCount;
    private Text       itemList;
    private List<Text> allFields;
    
    public LocationInfo() {
        this.currentTile    = null;
        this.tileName       = null;
        this.characterCount = null;
        this.characterList  = null;
        this.itemCount      = null;
        this.itemList       = null;
        this.allFields      = new ArrayList<>();
    }

    @Override
    public Node createUserInterface() {
        GridPane grid = new GridPane();
        
        int row = this.createTileName(grid, 0);
        row = this.createCreatures(grid, row);
        row = this.createItems(grid, row);

        grid.getChildren().addAll(this.allFields);
        
        return grid;
    }
    
    private int createTileName(GridPane grid, int row) {
        this.tileName = UserInterfaceFactory.createSmallText("");
        GridPane.setConstraints(this.tileName, 0, row);
        this.allFields.add(this.tileName);
        return row + 1;
    }
    
    private int createCreatures(GridPane grid, int row) {
        Text label = UserInterfaceFactory.createSmallText("Creatures:");
        GridPane.setConstraints(label, 0, row);
        grid.getChildren().add(label);

        this.characterCount = UserInterfaceFactory.createSmallText("");
        GridPane.setConstraints(this.characterCount, 1, row);
        this.allFields.add(this.characterCount);

        row++;
        
        this.characterList = UserInterfaceFactory.createSmallText("");
        this.characterList.setWrappingWidth(100);
        GridPane.setConstraints(this.characterList, 0, row);
        GridPane.setColumnSpan(this.characterList, 2);
        this.allFields.add(this.characterList);

        return row + 1;
    }        

    private int createItems(GridPane grid, int row) {
        Text label = UserInterfaceFactory.createSmallText("Items:");
        GridPane.setConstraints(label, 0, row);
        grid.getChildren().add(label);

        this.itemCount = UserInterfaceFactory.createSmallText("");
        GridPane.setConstraints(this.itemCount, 1, row);
        this.allFields.add(this.itemCount);

        row++;
        
        this.itemList = UserInterfaceFactory.createSmallText("");
        this.itemList.setWrappingWidth(100);
        GridPane.setConstraints(this.itemList, 0, row);
        GridPane.setColumnSpan(this.itemList, 2);
        this.allFields.add(this.itemList);

        return row + 1;
    }        

    @Override
    public void refresh() {
        if (this.currentTile != null) {
            List<Character>  characters = new ArrayList<>();
            List<GameObject> items      = new ArrayList<>();
            for (GameObject object : this.currentTile.getObjects(null)) {
                if (object instanceof Character) {
                    if (!object.getObjectType().equals("player")) {
                        characters.add((Character) object);
                    }
                } else {
                    items.add(object);
                }
            }

            this.refreshTileName();
            int row = this.refreshCreatures(0, characters);
            row = this.refreshItems(row, items);
            
        } else {
            this.allFields.forEach(field -> field.setText(""));
        }
    }

    private void refreshTileName() {
        this.tileName.setText(this.currentTile.getName());
    }        
    
    private int refreshCreatures(int row, List<Character> creatures) {
        this.characterCount.setText("" + creatures.size());
        this.characterList.setText(" " + creatures.stream().map(Character::getName).collect(Collectors.joining(", ")));
        return row + 1;
    }
    
    private int refreshItems(int row, List<GameObject> items) {
        this.itemCount.setText("" + items.size());
        this.itemList.setText(" " + items.stream().map(GameObject::getName).collect(Collectors.joining(", ")));
        return row + 1;
    }        
    
    /**
     * Set the tile to be used for the information.
     * 
     * @param tile The target tile
     */
    public void setTile(Tile tile) {
        if (this.currentTile != tile) {
            this.currentTile = tile;
        }
    }
}
