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
 * Helper methods to create UI elements:
 *
 * @author joyr
 */
public class UserInterfaceFactory {
    
    public static Node createLogo() {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        Image logo = ImageCache.getInstance().get("images/Logo");
        ImageView logov = new ImageView(logo);
        hb.getChildren().add(logov);
        hb.getStyleClass().add("logo");
        return hb;
    }
        
    
    public static Text createTitle(String title) {
        Text t = new Text();
        t.setFont(Font.font("Tahoma", FontWeight.BOLD, 50));
        t.setTextAlignment(TextAlignment.CENTER);
        t.setText(title);
        t.setFill(Color.WHITE);
        return t;
    }
    
    
    public static Region createVerticalSpacer(int height) {
        Region spacer = new Region();
        spacer.setOpacity(0.0);
        spacer.setMinHeight(height);
        return spacer;
    }
    

    public static Text createText(String contents, int size) {
        Text rv = new Text();
        rv.setFont(Font.font("Tahoma", size));
        rv.setText(contents);
        rv.setFill(Color.WHITE);
        return rv;
    }

    public static Text createLargeText(String contents) {
        return UserInterfaceFactory.createText(contents, 20);
    }
    
    public static Text createText(String contents) {
        return UserInterfaceFactory.createText(contents, 16);
    }
    
    
    public static Text createSmallText(String contents) {
        return UserInterfaceFactory.createText(contents, 10);
    }
    
    
    public static Node createLabeledInput(String label, Node input) {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
            
        Text l = UserInterfaceFactory.createText(label);
        hb.getChildren().addAll(l, input);
            
        return hb;
    }
    
    
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
