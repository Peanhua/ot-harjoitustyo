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

import fishingrodofdestiny.resources.StatisticsCache;
import fishingrodofdestiny.savedata.statistics.Statistics;
import fishingrodofdestiny.ui.widgets.Starfield;
import fishingrodofdestiny.ui.widgets.UserInterfaceFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The screen responsible of displaying the statistics of all played games.
 * 
 * @author joyr
 */
public class ScreenStatistics extends Screen {
    private Starfield starfield;

    public ScreenStatistics(Screen parent, Stage stage) {
        super(parent, stage);
        this.starfield = null;
    }

    @Override
    protected Node createUserInterface() {
        StackPane sp = new StackPane();

        this.starfield = new Starfield(Starfield.Direction.RIGHT);
        sp.getChildren().add(this.starfield.createUserInterface());
        
        BorderPane pane = new BorderPane();
        sp.getChildren().add(pane);
        
        pane.setTop(UserInterfaceFactory.createLogo());
        
        pane.setCenter(this.createStatisticsDisplay());

        String[] labels = { "Close" };
        List<Button> buttons = new ArrayList<>();
        Node buttonsNode = UserInterfaceFactory.createButtonRow(labels, buttons);
        buttonsNode.getStyleClass().add("bottomButton");
        pane.setBottom(buttonsNode);
        buttons.get(0).setOnAction(e -> this.close());
        
        return sp;
        
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
        this.addStatistics(stats, grid, 0);
        
        return pane;
    }
    
    private int addStatistics(Statistics stats, GridPane grid, int row) {
        double completionPercentage = 0.0;
        if (stats.getGamesPlayed() > 0) {
            completionPercentage = (double) stats.getGamesCompleted() * 100.0 / (double) stats.getGamesPlayed();
        }
        this.addStatisticsRow(grid, row++, "Games played:",         "" + stats.getGamesPlayed());
        this.addStatisticsRow(grid, row++, "Games completed:",      "" + stats.getGamesCompleted() + " (" + String.format("%.2f", completionPercentage) + "%)");
        this.addStatisticsRow(grid, row++, "Gold coins collected:", "" + stats.getGoldCoinsCollected());
        this.addStatisticsRow(grid, row++, "Enemies killed:",       "" + stats.getEnemiesKilled());
        return row;
    }
    
    private void addStatisticsRow(GridPane grid, int row, String label, String value) {
        Text labelText = UserInterfaceFactory.createText(label);
        Text valueText = UserInterfaceFactory.createText(value);
        GridPane.setConstraints(labelText, 0, row);
        GridPane.setConstraints(valueText, 1, row);
        grid.getChildren().addAll(labelText, valueText);
    }
    
    
    @Override
    public void onShow() {
        this.starfield.enable();
    }
}
