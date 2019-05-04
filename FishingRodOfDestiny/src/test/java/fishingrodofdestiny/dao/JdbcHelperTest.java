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
package fishingrodofdestiny.dao;

import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joyr
 */
public class JdbcHelperTest {
    
    JdbcHelper helper;
    
    @Before
    public void setUp() {
        helper = new JdbcHelper("jdbc:sqlite:file::memory:?cache=shared");
    }
    
    @Test
    public void connectionTest() {
         assertNotNull(helper);
    }
    
    @Test
    public void select1() throws SQLException {
        CachedRowSet rs = helper.query("SELECT 1");
        rs.next();
        assertEquals(1, rs.getInt(1));
    }
    
    @Test
    public void createTable() throws SQLException {
        assertTrue(helper.update("CREATE TABLE createTableTest ( bar INT )"));
    }

    @Test
    public void insertRow() throws SQLException {
        helper.update("CREATE TABLE insertRowTest ( bar INTEGER PRIMARY KEY AUTOINCREMENT, kek VARCHAR(40) )");
        Integer id = helper.insert("INSERT INTO insertRowTest ( kek ) VALUES ( 'kak' )");
        assertNotNull(id);
    }

    @Test
    public void insertedRowCanBeQueried() throws SQLException {
        helper.update("CREATE TABLE iqTest ( bar INTEGER PRIMARY KEY AUTOINCREMENT, kek VARCHAR(40) )");
        helper.insert("INSERT INTO iqTest ( kek ) VALUES ( 'kak' )");
        CachedRowSet rs = helper.query("SELECT kek FROM iqTest");
        rs.next();
        String result = rs.getString(1);
        assertTrue(result.equals("kak"));
    }
    
    @Test
    public void insertPreparerWorks() throws SQLException {
        helper.update("CREATE TABLE insertPreparerWorks ( bar INTEGER PRIMARY KEY AUTOINCREMENT, kek VARCHAR(40) )");
        helper.insert("INSERT INTO insertPreparerWorks ( kek ) VALUES ( ? )", (stmt) -> {
                stmt.setString(1, "kak");
        });
        CachedRowSet rs = helper.query("SELECT kek FROM insertPreparerWorks");
        rs.next();
        String result = rs.getString(1);
        assertTrue(result.equals("kak"));
    }
    
    @Test
    public void erroneousQuery() {
        assertNull(helper.query("this is illegal sql and does not work"));
    }

    @Test
    public void erroneousInsert() {
        assertNull(helper.insert("this is illegal sql and does not work"));
    }
    
    @Test
    public void erroneousUpdate() {
        assertFalse(helper.update("this is illegal sql and does not work"));
    }

}
