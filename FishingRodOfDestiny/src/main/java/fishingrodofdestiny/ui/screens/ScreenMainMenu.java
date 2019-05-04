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

import fishingrodofdestiny.resources.ImageCache;
import fishingrodofdestiny.resources.SettingsCache;
import fishingrodofdestiny.ui.widgets.Starfield;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import fishingrodofdestiny.ui.windows.SettingsEditor;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The main menu screen, first screen opened when the software starts.
 * 
 * @author joyr
 */
public class ScreenMainMenu extends Screen {
    private Starfield bubbles;
    private VBox      buttons;
    
    public ScreenMainMenu(Screen parent, Stage stage) {
        super(parent, stage);
        this.bubbles = null;
        // Load settings, not really necessary, but it's nicer for the user to see possible errors in the settings as soon as possible.
        SettingsCache.getInstance();
    }

    @Override
    protected Node createUserInterface() {
        StackPane sp = new StackPane();
        
        Image layer0Image = ImageCache.getInstance().get("images/MainMenuBackground-layer0");
        ImageView layer0 = new ImageView(layer0Image);
        sp.getChildren().add(layer0);
        
        this.bubbles = new Starfield(Starfield.Direction.UP);
        sp.getChildren().add(this.bubbles.createUserInterface());

        Image layer1Image = ImageCache.getInstance().get("images/MainMenuBackground-layer1");
        ImageView layer1 = new ImageView(layer1Image);
        sp.getChildren().add(layer1);

        Region bgOverlay = new Region();
        bgOverlay.setOpacity(0.66);
        sp.getChildren().add(bgOverlay);
        
        BorderPane pane = new BorderPane();
        sp.getChildren().add(pane);
        
        pane.setTop(UserInterfaceFactory.createLogo());
        pane.setCenter(this.setupButtons());

        return sp;
    }

    private Node setupButtons() {
        this.buttons = new VBox(2);
        this.buttons.setAlignment(Pos.CENTER);
            
        Button newgame = new Button("New Game");
        newgame.setOnAction(e -> this.startNewGame());

        Button highscores = new Button("Highscores");
        highscores.setOnAction(e -> this.showHighscores());
        
        Button stats = new Button("Statistics");
        stats.setOnAction(e -> this.showStatistics());
        
        Button settings = new Button("Settings");
        settings.setOnAction(e -> this.showSettings());

        Button quit = new Button("Quit");
        quit.setOnAction(e-> this.close());
            
        buttons.getChildren().addAll(newgame, highscores, stats, settings, quit);

        return buttons;
    }
    
    private void startNewGame() {
        this.bubbles.disable();
        Screen ng = new ScreenNewGame(this, this.getStage());
        ng.show();
    }
    
    
    private void showHighscores() {
        this.bubbles.disable();
        Screen hs = new ScreenHighscores(this, this.getStage());
        hs.show();
    }
    
    private void showStatistics() {
        this.bubbles.disable();
        Screen s = new ScreenStatistics(this, this.getStage());
        s.show();
    }
    
    private void showSettings() {
        SettingsEditor editor = new SettingsEditor(this);
        editor.show();
    }
    
    @Override
    public void enableInputHandlers() {
        this.buttons.setDisable(false);
    }
    
    @Override
    public void disableInputHandlers() {
        this.buttons.setDisable(true);
    }

    @Override
    public void onShow() {
        this.bubbles.enable();
    }
}
