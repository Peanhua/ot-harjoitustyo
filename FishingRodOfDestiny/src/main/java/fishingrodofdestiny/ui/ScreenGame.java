/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui;

import fishingrodofdestiny.ui.widgets.LevelView;
import fishingrodofdestiny.ui.widgets.CharacterStatus;
import fishingrodofdestiny.world.Game;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGame extends Screen {
    private Game game;
    
    public ScreenGame(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game = game;
    }

    @Override
    protected void setup(Group root, Scene scene) {
        HBox hb = new HBox(20);
        
        VBox leftbox = new VBox(0);
        hb.getChildren().add(leftbox);

        CharacterStatus status = new CharacterStatus(this.game.getPlayer());
        leftbox.getChildren().add(status.createUserInterface());


        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);

        Button quit = new Button("Quit");
        quit.setOnAction(e-> this.close());
        buttons.getChildren().addAll(quit);
            
        leftbox.getChildren().add(buttons);
        
        
        LevelView lview = new LevelView((int) scene.getWidth() - 20 - (int) leftbox.getBoundsInParent().getWidth(),
                                        (int) scene.getHeight());
        hb.getChildren().add(lview.createUserInterface());
        lview.setLevel(this.game.getPlayer().getLocation().getContainerTile().getInLevel());
        
        root.getChildren().add(hb);
    }
}
