/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.highscores.Highscore;
import fishingrodofdestiny.highscores.HighscoreList;
import fishingrodofdestiny.highscores.ScoreBasedHighscore;
import fishingrodofdestiny.resources.HighscoreListCache;
import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.ui.widgets.LevelView;
import fishingrodofdestiny.ui.widgets.CharacterStatus;
import fishingrodofdestiny.ui.widgets.ConfirmationRequester;
import fishingrodofdestiny.ui.widgets.LocationInfo;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.gameobjects.GameObject;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGame extends Screen {
    private Game         game;
    private StackPane    root;
    private Node         gameView;
    private LevelView    levelView;
    private Text         message;
    private LocationInfo locationInfo;
    
    public ScreenGame(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game         = game;
        this.root         = null;
        this.gameView     = null;
        this.levelView    = null;
        this.message      = null;
        this.locationInfo = null;
    }

    @Override
    protected Node createUserInterface() {
        this.root = new StackPane();
        
        BorderPane main = new BorderPane();
        this.gameView = main;
        this.root.getChildren().add(main);
        
        VBox leftbox = new VBox(20);
        main.setLeft(leftbox);

        CharacterStatus status = new CharacterStatus(this.game.getPlayer());
        leftbox.getChildren().add(status.createUserInterface());
        
        this.locationInfo = new LocationInfo();
        leftbox.getChildren().add(this.locationInfo.createUserInterface());

        Button quit = new Button("Quit");
        quit.setFocusTraversable(false);
        quit.setOnAction(e-> this.onEndGameClicked());
        leftbox.getChildren().add(quit);

        this.levelView = new LevelView();
        Node lv = this.levelView.createUserInterface();
        main.setCenter(lv);

        this.message = UserInterfaceFactory.createText("Welcome to The Fishing Rod of Destiny!");
        main.setBottom(this.message);
        
        
        this.game.getPlayer().getLocation().listenOnChange(() -> {
            this.onPlayerMoved();
        });
        
        this.setKeyboardHandlers();
        this.enableInputHandlers();
        
        this.onPlayerMoved();
        
        return root;
    }
    
    
    private void handleCommand(KeyboardSettings.Command command) {
        switch (command) {
            case ZOOM_OUT:
                this.levelView.setTileSize(this.levelView.getTileSize() / 2);
                this.levelView.refresh();
                break;
            case ZOOM_IN:
                this.levelView.setTileSize(this.levelView.getTileSize() * 2);
                this.levelView.refresh();
                break;
            case EXIT:
                this.onEndGameClicked();
                break;
        }
    }
    
    private void enableInputHandlers() {
        this.gameView.setFocusTraversable(true);
    }
    
    private void disableInputHandlers() {
        this.gameView.setFocusTraversable(false);
    }
    
    private void setKeyboardHandlers() {
        this.gameView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            KeyboardSettings settings = KeyboardSettings.getInstance();
            
            KeyboardSettings.Command command = settings.getCommand(event.getCode());
            if (command != null) {
                this.handleCommand(command);
                return;
            }
            
            GameObject.Action action = settings.getAction(event.getCode());
            if (action == null) {
                return;
            }
            
            GameObject player = this.game.getPlayer();
            if (player == null || !player.isAlive()) {
                return;
            }

            this.game.getPlayer().setNextAction(action);
            this.game.tick();
            this.message.setText(this.game.getPlayer().popMessage());
            this.levelView.refresh();
        });
    }
    
    
    private void onPlayerMoved() {
        Tile tile = this.game.getPlayer().getLocation().getContainerTile();
        if (tile != null) {
            this.levelView.setLevel(tile.getLevel());
            this.levelView.centerAtTile(tile.getX(), tile.getY());
        }
        
        if (this.game.getPlayer().isAlive()) {
            this.locationInfo.setTile(tile);
        } else {
            this.locationInfo.refresh();
        }
    }
    
    
    private void onEndGameClicked() {
        if (this.game.getPlayer().isAlive()) {
            ConfirmationRequester cr = new ConfirmationRequester("You are still alive!\nAre you sure you want to exit?", "Cancel", "Exit");
            this.disableInputHandlers();
            List<Button> buttons = cr.show(this.root);
            
            buttons.get(0).setOnAction(e -> {
                cr.close();
                this.enableInputHandlers();
            });
            
            buttons.get(1).setOnAction(e -> {
                cr.close();
                endGame();
            });
            
        } else {
            endGame();
        }
    }

    private void endGame() {
        Highscore hs = new ScoreBasedHighscore(this.game);

        HighscoreListCache hc = HighscoreListCache.getInstance();
        HighscoreList hslist = hc.get(Highscore.Type.SCORE);
        hslist.add(hs);

        this.close();
    }
}
