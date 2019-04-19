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
import fishingrodofdestiny.resources.StatisticsCache;
import fishingrodofdestiny.settings.KeyboardSettings;
import fishingrodofdestiny.statistics.Statistics;
import fishingrodofdestiny.ui.widgets.LevelView;
import fishingrodofdestiny.ui.widgets.CharacterStatus;
import fishingrodofdestiny.ui.windows.ConfirmationRequester;
import fishingrodofdestiny.ui.widgets.LocationInfo;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.controllers.Controller;
import fishingrodofdestiny.world.controllers.PlayerController;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenGame extends Screen {
    private final Game   game;
    private Node         gameView;
    private LevelView    levelView;
    private Text         message;
    private LocationInfo locationInfo;
    
    public ScreenGame(Game game, Screen parent, Stage stage) {
        super(parent, stage);
        this.game         = game;
        this.gameView     = null;
        this.levelView    = null;
        this.message      = null;
        this.locationInfo = null;
    }

    @Override
    protected Node createUserInterface() {
        
        BorderPane main = new BorderPane();
        this.gameView = main;
        
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

        this.levelView = new LevelView(this.game.getPlayer());
        Node lv = this.levelView.createUserInterface();
        main.setCenter(lv);

        this.message = UserInterfaceFactory.createText("Welcome to The Fishing Rod of Destiny!");
        main.setBottom(this.message);
        
        Player player = this.game.getPlayer();
        player.getLocation().listenOnChange(() -> {
            this.onPlayerMoved();
        });
        player.getController().listenOnNewAction(() -> {
            this.game.tick();
            this.message.setText(player.popMessage());
            this.locationInfo.refresh();
            this.levelView.refresh();
            
            if (player.getGameCompleted()) {
                this.gameCompleted();
            }
        });
        
        this.setKeyboardHandlers();
        this.enableInputHandlers();
        
        this.onPlayerMoved();
        this.locationInfo.refresh();
        // The levelView doesn't need to be refresh()'d here because it will receive a resize event which will cause the refresh() to happen.
        
        return main;
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
    
    
    private boolean forwardJavaFXEventToPlayerController(KeyEvent event) {
        Player player = this.game.getPlayer();
        if (player == null || !player.isAlive()) {
            return false;
        }
        
        Controller controller = player.getController();
        if (controller == null || !(controller instanceof PlayerController)) {
            return false;
        }
        PlayerController pc = (PlayerController) controller;

        return pc.handleJavaFXEvent(this, event);
    }        
    
    
    @Override
    public void enableInputHandlers() {
        this.gameView.setFocusTraversable(true);
    }
    
    @Override
    public void disableInputHandlers() {
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
            
            this.forwardJavaFXEventToPlayerController(event);
        });
    }
    
    
    private void onPlayerMoved() {
        Player player = this.game.getPlayer();
        Tile tile = player.getLocation().getContainerTile();
        if (tile != null) {
            this.levelView.setLevel(tile.getLevel());
            this.levelView.centerAtTile(tile.getX(), tile.getY());
        }
        if (player.isAlive()) {
            this.locationInfo.setTile(tile);
        }
    }
    
    
    private void onEndGameClicked() {
        if (this.game.getPlayer().isAlive()) {
            ConfirmationRequester cr = new ConfirmationRequester(this, "You are still alive!\nAre you sure you want to exit?", "Cancel", "Exit", () -> {
                this.endGame();
            });
            cr.show();
        } else {
            this.endGame();
        }
    }
    
    private void recordStatistics() {
        Statistics stats = StatisticsCache.getInstance().getStatistics();
        stats.addFromGame(game);
    }
    
    private Highscore createHighscore() {
        Highscore hs = new ScoreBasedHighscore(this.game);

        HighscoreListCache hc = HighscoreListCache.getInstance();
        HighscoreList hslist = hc.get(Highscore.Type.SCORE);
        hslist.add(hs);

        return hs;
    }

    private void endGame() {
        this.close();
        this.recordStatistics();
        this.createHighscore();
    }
    
    private void gameCompleted() {
        this.close();
        
        this.recordStatistics();
        
        Highscore hs = this.createHighscore();
        Screen screen = new ScreenGameCompletion(this.game, this.getParent(), this.getStage());
        screen.show();
    }
}
