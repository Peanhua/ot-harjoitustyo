/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

        Button loadgame = new Button("Load Saved Game");
        loadgame.setOnAction(e -> this.loadGame());
        loadgame.setDisable(true);

        Button highscores = new Button("Highscores");
        highscores.setOnAction(e -> this.showHighscores());
        
        Button stats = new Button("Statistics");
        stats.setOnAction(e -> this.showStatistics());
        
        Button settings = new Button("Settings");
        settings.setOnAction(e -> this.showSettings());

        Button quit = new Button("Quit");
        quit.setOnAction(e-> this.close());
            
        buttons.getChildren().addAll(newgame, loadgame, highscores, stats, settings, quit);

        return buttons;
    }
    
    private void startNewGame() {
        this.bubbles.disable();
        Screen ng = new ScreenNewGame(this, this.getStage());
        ng.show();
    }
    
    
    private void loadGame() {
        this.bubbles.disable();
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
