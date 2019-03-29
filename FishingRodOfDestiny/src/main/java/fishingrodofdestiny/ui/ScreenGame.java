/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGame extends Screen {
    public ScreenGame(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);

        vb.getChildren().add(UserInterfaceFactory.createLogo(scene));

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(500));

        {
            VBox buttons = new VBox(10);
            buttons.setAlignment(Pos.CENTER);

            Button quit = new Button("Quit");
            quit.setOnAction(e-> this.close());
            buttons.getChildren().addAll(quit);
            
            vb.getChildren().add(buttons);
        }
        
        root.getChildren().add(vb);
    }
}