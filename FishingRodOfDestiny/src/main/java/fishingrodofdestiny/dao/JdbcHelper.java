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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

/**
 * Helper class to handle database operations using Jdbc.
 * 
 * @author joyr
 */
public class JdbcHelper {
    
    public interface StatementPreparer {
        /**
         * Called after the SQL statement has been prepared.
         * <p>
         * Can be used for example to set query parameters.
         * 
         * @param stmt The statement that is prepared.
         * @throws SQLException May throw SQLException
         */
        public void prepare(PreparedStatement stmt) throws SQLException;
    }
    
    private String        databaseUrl;
    private RowSetFactory rowSetFactory;
    private Connection    connection;
    private boolean       debugging;

    public JdbcHelper(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        try {
            this.rowSetFactory = RowSetProvider.newFactory();
        } catch (Exception e) {
            this.rowSetFactory = null;
        }
        this.connection = null;
        this.debugging = false;
    }
   
    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }
    
    private void openConnection() throws SQLException {
        if (this.connection != null) {
            if (!this.connection.isValid(1)) {
                this.connection.close();
                this.connection = null;
            }
        }
        if (this.connection == null) {
            this.connection = DriverManager.getConnection(this.databaseUrl);
        }
    }
    
    /**
     * Do a normal read-only SQL query.
     * @param sql The SQL clause to execute
     * @return Rows of data
     */
    public CachedRowSet query(String sql) {
        return this.query(sql, null);
    }

    /**
     * Do a normal read-only SQL query with a statement preparer.
     * <p>
     * The statement preparer can be used for example to set the query parameters.
     * 
     * @param sql The SQL clause to execute
     * @param preparer The statement preparer 
     * @return Rows of data
     */
    public CachedRowSet query(String sql, StatementPreparer preparer) {
        if (this.rowSetFactory == null) {
            return null;
        }

        try {
            this.openConnection();

            PreparedStatement stmt = connection.prepareStatement(sql);
            this.applyPreparer(stmt, preparer);
            ResultSet rs = stmt.executeQuery();

            CachedRowSet rv = this.rowSetFactory.createCachedRowSet();
            rv.populate(rs);

            stmt.close();
            rs.close();
            
            return rv;
            
        } catch (Exception e) {
            this.debugMessage("JdbcHelper.query(" + sql + "): " + e);
            return null;
        }
    }
    
    
    private void debugMessage(String message) {
        if (this.debugging) {
            System.out.println(message);
        }
    }
    
    
    /**
     * Perform an SQL query updating the database.
     * 
     * @param sql The SQL query to execute
     * @return True if the operation was successful
     */
    public boolean update(String sql) {
        return this.update(sql, null);
    }
    
    /**
     * Perform an SQL query updating the database with a statement preparer.
     * <p>
     * The statement preparer can be used for example to set the query parameters.
     * 
     * @param sql The SQL query to execute
     * @param preparer The statement preparer
     * @return True if the operation was successful
     */
    public boolean update(String sql, StatementPreparer preparer) {
        try {
            this.openConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            this.applyPreparer(stmt, preparer);
            stmt.executeUpdate();

            stmt.close();

            return true;
            
        } catch (Exception e) {
            this.debugMessage("JdbcHelper.update(" + sql + "): " + e);
            return false;
        }
    }

    
    /**
     * Perform an SQL insert query.
     * 
     * @param sql The SQL query to execute
     * @return The primary key of the newly inserted row, or null upon failure
     */
    public Integer insert(String sql) {
        return this.insert(sql, null);
    }

    /**
     * Perform an SQL insert query  with a statement preparer.
     * <p>
     * The statement preparer can be used for example to set the query parameters.
     * 
     * @param sql The SQL query to execute
     * @param preparer The statement preparer
     * @return The primary key of the newly inserted row, or null upon failure
     */
    public Integer insert(String sql, StatementPreparer preparer) {
        try {
            this.openConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.applyPreparer(stmt, preparer);
            stmt.executeUpdate();

            Integer id = this.getGeneratedKey(stmt);
            
            stmt.close();

            return id;
            
        } catch (Exception e) {
            this.debugMessage("JdbcHelper.insert(" + sql + "): " + e);
            return null;
        }
    }

    
    private void applyPreparer(PreparedStatement stmt, StatementPreparer preparer) {
        if (preparer != null) {
            try {
                preparer.prepare(stmt);
            } catch (Exception e) {
                this.debugMessage("JdbcHelper.applyPreparer(): " + e);
            }
        }
    }

        
    private Integer getGeneratedKey(PreparedStatement stmt) {
        Integer id = null;
        try {
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        } catch (Exception e) {
            this.debugMessage("JdbcHelper.getGeneratedKey(): " + e);
            return null;
        }

        return id;
    }
}
