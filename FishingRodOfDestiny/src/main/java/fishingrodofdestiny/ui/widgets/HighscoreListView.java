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
package fishingrodofdestiny.ui.widgets;

import fishingrodofdestiny.savedata.highscores.Highscore;
import fishingrodofdestiny.savedata.highscores.HighscoreList;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * Shows a highscore list.
 * 
 * @author joyr
 */
public class HighscoreListView extends Widget {
    private final HighscoreList highscoreList;
    private final List<Text> names;
    private final List<Text> points;
    private final List<Text> timestamps;
    
    public HighscoreListView(HighscoreList highscoreList) {
        this.highscoreList = highscoreList;
        this.names         = new ArrayList<>();
        this.points        = new ArrayList<>();
        this.timestamps    = new ArrayList<>();
    }

    @Override
    public Node createUserInterface() {
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(4);
        grid.setVgap(2);

        int row = this.createHeaders(grid, 0);
        row = this.createEntryList(grid, row);

        this.refresh();
        return grid;
    }
    
    private int createHeaders(GridPane grid, int row) {
        String[] headers = { "#", "Name", this.highscoreList.getType().toString(), "Date/time" };
        for (int i = 0; i < headers.length; i++) {
            Text label = UserInterfaceFactory.createLargeText(headers[i]);
            label.setUnderline(true);
            GridPane.setConstraints(label, i, row);
            grid.getChildren().add(label);
        }
        row++;
        return row;
    }

    private int createEntryList(GridPane grid, int row) {
        for (int i = 0; i < this.highscoreList.getMaximumNumberOfEntries(); i++) {
            Text pos = UserInterfaceFactory.createText("" + (i + 1));
            GridPane.setConstraints(pos, 0, row);

            Text nam = UserInterfaceFactory.createText("");
            GridPane.setConstraints(nam, 1, row);
            this.names.add(nam);

            Text pnt = UserInterfaceFactory.createText("");
            GridPane.setConstraints(pnt, 2, row);
            this.points.add(pnt);

            Text tst = UserInterfaceFactory.createText("");
            GridPane.setConstraints(tst, 3, row);
            this.timestamps.add(tst);

            grid.getChildren().addAll(pos, nam, pnt, tst);

            row++;
        }
        return row;
    }
    
    @Override
    public void refresh() {
        for (int i = 0; i < this.highscoreList.getMaximumNumberOfEntries(); i++) {
            String nam = "";
            String pts = "";
            String tst = "";
            
            Highscore hs = this.highscoreList.get(i);
            if (hs != null) {
                nam = hs.getName();
                pts = "" + hs.getPoints();
                tst = hs.getEndTimestamp().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
            
            this.names.get(i).setText(nam);
            this.points.get(i).setText(pts);
            this.timestamps.get(i).setText(tst);
        }
    }
}
