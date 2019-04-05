/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.world.gameobjects.Game;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenPlot extends Screen {
    private Game game;
    
    public ScreenPlot(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game = game;
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(50));
        vb.getChildren().add(UserInterfaceFactory.createLogo(scene));

        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(100));

        Text t = UserInterfaceFactory.createText("A princess/prince has fallen into the Lake of Sunken Nobles!\n\n"
                                               + "You must rescue her/him by retrieving the Magical Fishing Rod from the depths of cave Caerrbannogh!");
        t.setFont(new Font(20));
        t.setWrappingWidth(500);
        t.setTextAlignment(TextAlignment.CENTER);
        vb.getChildren().add(t);
        
        vb.getChildren().add(UserInterfaceFactory.createVerticalSpacer(100));
        
        Button cont = new Button("Continue");
        cont.setOnAction(e-> this.gameOn());
        vb.getChildren().add(cont);
        
        root.getChildren().add(vb);
    }
    
    
    private void gameOn() {
        this.close();
        
        Screen screen = new ScreenGame(this.game, this.getParent(), this.getStage());
        screen.show();
    }
}
