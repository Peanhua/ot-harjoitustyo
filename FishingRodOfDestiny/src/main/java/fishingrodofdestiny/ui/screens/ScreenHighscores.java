/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.savedata.highscores.Highscore;
import fishingrodofdestiny.savedata.highscores.HighscoreList;
import fishingrodofdestiny.resources.HighscoreListCache;
import fishingrodofdestiny.ui.widgets.HighscoreListView;
import fishingrodofdestiny.ui.widgets.Starfield;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenHighscores extends Screen {
    private Starfield      starfield;
    private BorderPane     container;
    private Highscore.Type currentType;

    public ScreenHighscores(Screen parent, Stage stage) {
        super(parent, stage);
        this.starfield = null;
        this.currentType = Highscore.Type.SCORE;
    }

    @Override
    protected Node createUserInterface() {
        StackPane sp = new StackPane();

        this.starfield = new Starfield(Starfield.Direction.LEFT);
        sp.getChildren().add(this.starfield.createUserInterface());

        this.container = new BorderPane();
        sp.getChildren().add(this.container);

        this.container.setTop(UserInterfaceFactory.createLogo());
        
        this.refresh();
        
        this.container.setBottom(this.createButtons());
        
        return sp;
    }
    
    public void refresh() {
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);
        
        vb.getChildren().add(UserInterfaceFactory.createTitle(this.currentType.toString()));
        
        HighscoreList hslist = HighscoreListCache.getInstance().get(this.currentType);
        HighscoreListView hlv = new HighscoreListView(hslist);
        vb.getChildren().add(hlv.createUserInterface());
        this.container.setCenter(vb);
    }
    
    private Node createButtons() {
        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);
        
        String[] chooserLabels = { Highscore.Type.SCORE.toString(), Highscore.Type.ACTION_COUNT.toString() };
        List<Button> chooserButtons = new ArrayList<>();
        buttons.getChildren().add(UserInterfaceFactory.createButtonRow(chooserLabels, chooserButtons));
        chooserButtons.get(0).setOnAction(e -> this.switchTo(Highscore.Type.SCORE));
        chooserButtons.get(1).setOnAction(e -> this.switchTo(Highscore.Type.ACTION_COUNT));

        Button close = new Button("Close");
        close.setOnAction(e-> this.close());
        buttons.getChildren().addAll(close);
        buttons.getStyleClass().add("bottomButton");
        
        return buttons;
    }
    
    private void switchTo(Highscore.Type toType) {
        this.currentType = toType;
        this.refresh();
    }
    

    @Override
    public void onShow() {
        this.starfield.enable();
    }
}
