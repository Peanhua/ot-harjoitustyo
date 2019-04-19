/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishingrodofdestiny.dao;

import fishingrodofdestiny.statistics.Statistics;

/**
 *
 * @author joyr
 */
public interface StatisticsDao {
    public void load(Statistics to);
    public void save(Statistics from);
}
