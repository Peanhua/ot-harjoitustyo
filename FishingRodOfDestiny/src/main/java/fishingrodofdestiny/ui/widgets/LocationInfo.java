/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        Text label;
        
        int row = 0;
        
        this.tileName = UserInterfaceFactory.createSmallText("");
        GridPane.setConstraints(this.tileName, 0, row);
        this.allFields.add(this.tileName);

        row++;
        
        label = UserInterfaceFactory.createSmallText("Creatures:");
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

        row++;
        
        label = UserInterfaceFactory.createSmallText("Items:");
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

        row++;

        grid.getChildren().addAll(this.allFields);
        
        return grid;
    }

    @Override
    public void refresh() {
        if (this.currentTile != null) {
            int row = 0;
            this.tileName.setText(this.currentTile.getName());
            
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
            
            this.characterCount.setText("" + characters.size());
            this.characterList.setText(" " + characters.stream().map(Character::getName).collect(Collectors.joining(", ")));
            row++;
            
            this.itemCount.setText("" + items.size());
            this.itemList.setText(" " + items.stream().map(GameObject::getName).collect(Collectors.joining(", ")));
            row++;
            
        } else {
            this.allFields.forEach(field -> field.setText(""));
        }
    }
    

    public void setTile(Tile tile) {
        if (this.currentTile != tile) {
            this.currentTile = tile;
        }
    }
}
