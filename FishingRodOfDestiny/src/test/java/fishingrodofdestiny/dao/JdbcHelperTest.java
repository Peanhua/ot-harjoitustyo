/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
