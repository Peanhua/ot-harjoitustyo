/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.ui.screens;

import fishingrodofdestiny.resources.StatisticsCache;
import fishingrodofdestiny.statistics.Statistics;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author joyr
 */
public class ScreenStatistics extends Screen {

    public ScreenStatistics(Screen parent, Stage stage) {
        super(parent, stage);
    }

    @Override
    protected Node createUserInterface() {
        BorderPane pane = new BorderPane();

        pane.setTop(UserInterfaceFactory.createLogo());
        
        pane.setCenter(this.createStatisticsDisplay());

        String[] labels = { "Close" };
        List<Button> buttons = new ArrayList<>();
        pane.setBottom(UserInterfaceFactory.createButtonRow(labels, buttons));
        buttons.get(0).setOnAction(e -> this.close());
        
        return pane;
        
    }
    
    private Node createStatisticsDisplay() {
        Statistics stats = StatisticsCache.getInstance().getStatistics();
        
        BorderPane pane = new BorderPane();
        
        VBox vb = new VBox(0);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().add(UserInterfaceFactory.createTitle("STATISTICS"));
        pane.setTop(vb);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(2);
        pane.setCenter(grid);
        int row = 0;
        this.addStatisticsRow(grid, row++, "Games played:",         "" + stats.getGamesPlayed());
        this.addStatisticsRow(grid, row++, "Games completed:",      "" + stats.getGamesCompleted());
        this.addStatisticsRow(grid, row++, "Gold coins collected:", "" + stats.getGoldCoinsCollected());
        this.addStatisticsRow(grid, row++, "Enemies killed:",       "" + stats.getEnemiesKilled());
        
        return pane;
    }
    
    private void addStatisticsRow(GridPane grid, int row, String label, String value) {
        Text labelText = UserInterfaceFactory.createText(label);
        Text valueText = UserInterfaceFactory.createText(value);
        GridPane.setConstraints(labelText, 0, row);
        GridPane.setConstraints(valueText, 1, row);
        grid.getChildren().addAll(labelText, valueText);
    }
}
