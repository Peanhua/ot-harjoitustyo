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
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileStatisticsDao;
import fishingrodofdestiny.dao.JdbcStatisticsDao;
import fishingrodofdestiny.dao.MemoryStatisticsDao;
import fishingrodofdestiny.dao.StatisticsDao;
import fishingrodofdestiny.savedata.statistics.Statistics;

/**
 * Manage statistics: loading, saving, and caching.
 * <p>
 * Uses singleton pattern.
 * A dao object handles the actual loading/saving, determines the type of dao to use via environment variable.
 *
 * @author joyr
 */
public class StatisticsCache {
    private static StatisticsCache instance = null;
    
    /**
     * Returns the single instance of StatisticsCache object.
     * 
     * @return The StatisticsCache object
     */
    public static StatisticsCache getInstance() {
        if (StatisticsCache.instance == null) {
            StatisticsCache.instance = new StatisticsCache();
        }
        return StatisticsCache.instance;
    }
    

    private final Statistics statistics;
  
    private StatisticsCache() {
        String defaultUri = "jdbc:sqlite:FishingRodOfDestiny.db";
        String uri = System.getenv("FISHINGRODOFDESTINY_STATISTICS");
        if (uri == null) {
            uri = defaultUri;
        }
        
        String fileStart = "file:";
        StatisticsDao dao;
        if (uri.startsWith("jdbc:")) {
            dao = new JdbcStatisticsDao(uri);
        } else if (uri.startsWith(fileStart)) {
            dao = new FileStatisticsDao(uri.substring(fileStart.length()));
        } else {
            dao = new MemoryStatisticsDao();
        }
        this.statistics = new Statistics(dao);
        this.statistics.load();
    }
    
    /**
     * Returns the statistics.
     * 
     * @return The statistics
     */
    public Statistics getStatistics() {
        return this.statistics;
    }
}
