/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.widgets;

import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    
    public static Node createLogo(Scene scene) {
        Image logo = new Image("file:gfx/Logo.png", false);
        ImageView logov = new ImageView(logo);
        logov.setTranslateX((scene.getWidth() - logo.getWidth()) / 2);
        return logov;
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
    
    
    public static Node createRadiobuttonGrid(String[] labels, List<RadioButton> buttons) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(2);
        
        ToggleGroup group = new ToggleGroup();
        for (int i = 0; i < labels.length; i++) {
            Text label  = UserInterfaceFactory.createText(labels[i]);
            
            RadioButton button = new RadioButton();
            buttons.add(button);
            button.setToggleGroup(group);
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
