/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.resources;

import fishingrodofdestiny.dao.FileStatisticsDao;
import fishingrodofdestiny.dao.JdbcStatisticsDao;
import fishingrodofdestiny.dao.MemoryStatisticsDao;
import fishingrodofdestiny.dao.StatisticsDao;
import fishingrodofdestiny.statistics.Statistics;

/**
 *
 * @author joyr
 */
public class StatisticsCache {
    private static StatisticsCache instance = null;
    
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
    
    public Statistics getStatistics() {
        return this.statistics;
    }
}
