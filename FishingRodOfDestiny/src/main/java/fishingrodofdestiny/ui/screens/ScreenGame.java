/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.ui.widgets.LevelView;
import fishingrodofdestiny.ui.widgets.CharacterStatus;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.GameObject;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGame extends Screen {
    private Game      game;
    private LevelView levelView;
    
    public ScreenGame(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game      = game;
        this.levelView = null;
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
        
        
        this.levelView = new LevelView((int) scene.getWidth() - 20 - (int) leftbox.getBoundsInParent().getWidth(),
                                       (int) scene.getHeight());
        hb.getChildren().add(this.levelView.createUserInterface());
        this.levelView.setLevel(this.game.getPlayer().getLocation().getContainerTile().getLevel());
        
        this.setKeyboardHandlers(root);
        
        root.getChildren().add(hb);
    }

    
    private void setKeyboardHandlers(Node root) {
        root.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyboardSettings settings = KeyboardSettings.getInstance();
            GameObject.Action action = settings.getAction(event.getCode());
            if (action == null) {
                return;
            }
            
            GameObject player = this.game.getPlayer();
            if (player == null) {
                return;
            }
            
            this.game.getPlayer().act(action);
            this.levelView.refresh();
        });
    }
}
