/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.ui.widgets.LevelView;
import fishingrodofdestiny.ui.widgets.CharacterStatus;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGame extends Screen {
    private Game      game;
    private LevelView levelView;
    private Text      message;
    
    public ScreenGame(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game      = game;
        this.levelView = null;
    }

    @Override
    protected void setup(Group root, Scene scene) {
        VBox main = new VBox(0);
        
        HBox hb = new HBox(20);
        main.getChildren().add(hb);
        
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

        
        this.message = UserInterfaceFactory.createText("");
        main.getChildren().add(this.message);
        this.game.getPlayer().listenOnMessage(() -> {
            this.message.setText(this.game.getPlayer().getMessage());
        });

        
        this.levelView = new LevelView((int) scene.getWidth() - 20 - (int) leftbox.getBoundsInParent().getWidth(),
                                       (int) (scene.getHeight() - this.message.getBoundsInParent().getHeight()));
        hb.getChildren().add(this.levelView.createUserInterface());
        
        
        this.game.getPlayer().getLocation().listenOnChange(() -> {
            this.onPlayerMoved();
        });
        
        this.setKeyboardHandlers(root);
        
        root.getChildren().add(main);

        
        this.game.getPlayer().setMessage("Welcome to The Fishing Rod of Destiny!");
        this.onPlayerMoved();
        this.levelView.refresh();
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

            this.game.getPlayer().setNextAction(action);
            this.game.tick();
            this.levelView.refresh();
        });
    }
    
    
    private void onPlayerMoved() {
        this.levelView.setLevel(this.game.getPlayer().getLocation().getContainerTile().getLevel());
    }
}
