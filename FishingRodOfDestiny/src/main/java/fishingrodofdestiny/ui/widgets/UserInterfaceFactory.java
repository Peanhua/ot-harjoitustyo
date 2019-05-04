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

import fishingrodofdestiny.resources.ImageCache;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Helper methods to create common UI elements:
 *
 * @author joyr
 */
public class UserInterfaceFactory {
    
    /**
     * Create a large logo of the game.
     * 
     * @return Node containing the logo
     */
    public static Node createLogo() {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        Image logo = ImageCache.getInstance().get("images/Logo");
        ImageView logov = new ImageView(logo);
        hb.getChildren().add(logov);
        hb.getStyleClass().add("logo");
        return hb;
    }
        
    /**
     * Create a large title text.
     * 
     * @param title The title text
     * @return A node containing the title text
     */
    public static Text createTitle(String title) {
        Text t = new Text();
        t.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        t.setTextAlignment(TextAlignment.CENTER);
        t.setText(title);
        t.setFill(Color.WHITE);
        return t;
    }
    
    
    /**
     * Create a vertical spacer for layout purposes.
     * 
     * @param height The vertical size of the spacer
     * @return A node containing the spacer
     */
    public static Region createVerticalSpacer(int height) {
        Region spacer = new Region();
        spacer.setOpacity(0.0);
        spacer.setMinHeight(height);
        return spacer;
    }
    

    /**
     * Create text element of the given size.
     * 
     * @param contents The contents of the text element
     * @param size The size
     * @return A Text node containing the given text
     */
    public static Text createText(String contents, int size) {
        Text rv = new Text();
        rv.setFont(Font.font("Tahoma", size));
        rv.setText(contents);
        rv.setFill(Color.WHITE);
        return rv;
    }

    /**
     * Create large sized text element.
     * 
     * @param contents The contents of the text element
     * @return A Text node containing the given text
     */
    public static Text createLargeText(String contents) {
        return UserInterfaceFactory.createText(contents, 20);
    }
    
    /**
     * Create normal sized text element.
     * 
     * @param contents The contents of the text element
     * @return A Text node containing the given text
     */
    public static Text createText(String contents) {
        return UserInterfaceFactory.createText(contents, 16);
    }
    
    /**
     * Create small sized text element.
     * 
     * @param contents The contents of the text element
     * @return A Text node containing the given text
     */
    public static Text createSmallText(String contents) {
        return UserInterfaceFactory.createText(contents, 10);
    }
    
    /**
     * Create a label for the given input node, and place them side-by-side.
     * 
     * @param label The text for the label
     * @param input The input node for which to create the label
     * @return A node containing both the label and the input node
     */
    public static Node createLabeledInput(String label, Node input) {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
            
        Text l = UserInterfaceFactory.createText(label);
        hb.getChildren().addAll(l, input);
            
        return hb;
    }
    
    /**
     * Create a grid containing a list of radiobuttons.
     * 
     * @param labels      The labels to use for the radiobuttons
     * @param toggleGroup A ToggleGroup for the radiobuttons
     * @param buttons     Empty list to receive the created RadioButtons
     * @return A node containing all the radiobuttons and their labels.
     */
    public static Node createRadiobuttonGrid(String[] labels, ToggleGroup toggleGroup, List<RadioButton> buttons) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(2);
        
        for (int i = 0; i < labels.length; i++) {
            Text label  = UserInterfaceFactory.createText(labels[i]);
            
            RadioButton button = new RadioButton();
            buttons.add(button);
            button.setToggleGroup(toggleGroup);
            if (i == 0) {
                button.setSelected(true);
            }
            
            GridPane.setConstraints(label, 0, i);
            GridPane.setConstraints(button, 1, i);
            grid.getChildren().addAll(label, button);
        }

        return grid;
    }
    
    /**
     * Create a single row of buttons.
     * 
     * @param labels  The labels for the buttons
     * @param buttons Empty list to receive the created Buttons
     * @return A node containing all the buttons
     */
    public static Node createButtonRow(String[] labels, List<Button> buttons) {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        for (String label : labels) {
            Button b = new Button(label);
            buttons.add(b);
        }
        hb.getChildren().addAll(buttons);
        return hb;
    }
}
