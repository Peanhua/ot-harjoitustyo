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
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.savedata.highscores.Highscore;
import fishingrodofdestiny.savedata.highscores.ScoreBasedHighscore;
import fishingrodofdestiny.resources.HighscoreListCache;
import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.resources.StatisticsCache;
import fishingrodofdestiny.savedata.highscores.ActionCountBasedHighscore;
import fishingrodofdestiny.savedata.settings.KeyboardSettings;
import fishingrodofdestiny.savedata.statistics.Statistics;
import fishingrodofdestiny.ui.widgets.LevelView;
import fishingrodofdestiny.ui.widgets.CharacterStatus;
import fishingrodofdestiny.ui.windows.ConfirmationRequester;
import fishingrodofdestiny.ui.widgets.LocationInfo;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.ui.windows.SettingsEditor;
import fishingrodofdestiny.world.Game;
import fishingrodofdestiny.world.controllers.Controller;
import fishingrodofdestiny.world.controllers.PlayerController;
import fishingrodofdestiny.world.gameobjects.Player;
import fishingrodofdestiny.world.tiles.Tile;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The screen active when a game is played.
 * 
 * @author joyr
 */
public class ScreenGame extends Screen {
    private final Game   game;
    private BorderPane   gameView;
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
        this.gameView = new BorderPane();
        
        VBox leftbox = new VBox(20);
        this.gameView.setLeft(leftbox);

        Player player = this.game.getPlayer();
        CharacterStatus status = new CharacterStatus(player);
        this.locationInfo = new LocationInfo();
        leftbox.getChildren().addAll(status.createUserInterface(), this.locationInfo.createUserInterface(), this.createSettingsButton(), this.createQuitButton());
        
        this.levelView = new LevelView(player);
        this.gameView.setCenter(this.levelView.createUserInterface());

        this.message = UserInterfaceFactory.createText("Welcome to The Fishing Rod of Destiny!");
        this.gameView.setBottom(this.message);
        
        player.getLocation().listenOnChange(() -> this.onPlayerMoved());
        player.getController().listenOnNewAction(() -> this.onPlayerAction(player));
        
        this.setKeyboardHandlers();
        this.enableInputHandlers();
        
        this.onPlayerMoved();
        this.locationInfo.refresh();
        // The levelView doesn't need to be refresh()'d here because it will receive a resize event which will cause the refresh() to happen.
        
        return this.gameView;
    }
    
    private void onPlayerAction(Player player) {
        this.game.tick();
        
        String msg = player.popMessage();
        if (msg != null && msg.length() > 0) {
            System.out.println(msg);
        }
        
        this.message.setText(msg);
        this.locationInfo.refresh();
        this.levelView.refresh();

        if (player.getGameCompleted()) {
            this.gameCompleted();
        }
    }
    
    private Node createSettingsButton() {
        Button button = new Button("Settings");
        button.setFocusTraversable(false);
        button.setOnAction(e-> this.showSettings());
        return button;
    }
    
    private Node createQuitButton() {
        Button button = new Button("Quit");
        button.setFocusTraversable(false);
        button.setOnAction(e-> this.onEndGameClicked());
        return button;
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
            KeyboardSettings settings = SettingsCache.getInstance().getKeyboardSettings();
            
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
    
    private List<Highscore> createHighscores() {
        HighscoreListCache hc = HighscoreListCache.getInstance();

        List<Highscore> scores = new ArrayList<>();
        
        scores.add(new ScoreBasedHighscore(this.game));
        hc.get(Highscore.Type.SCORE).add(scores.get(scores.size() - 1));

        if (this.game.getPlayer().getGameCompleted()) {
            scores.add(new ActionCountBasedHighscore(this.game));
            hc.get(Highscore.Type.ACTION_COUNT).add(scores.get(scores.size() - 1));
        }

        return scores;
    }

    private void endGame() {
        this.close();
        this.recordStatistics();
        this.createHighscores();
    }
    
    private void gameCompleted() {
        this.close();
        
        this.recordStatistics();
        
        this.createHighscores();
        Screen screen = new ScreenGameCompletion(this.game, this.getParent(), this.getStage());
        screen.show();
    }
    
    
    private void showSettings() {
        SettingsEditor editor = new SettingsEditor(this);
        editor.show();
    }        
}
