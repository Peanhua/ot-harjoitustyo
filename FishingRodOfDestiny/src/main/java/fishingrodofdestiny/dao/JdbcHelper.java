/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author joyr
 */
public class JdbcHelper {
    
    public interface StatementPreparer {
        void prepare(PreparedStatement stmt) throws SQLException;
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
    
    
    public CachedRowSet query(String sql) {
        return this.query(sql, null);
    }
    
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
            if (this.debugging) {
                System.out.println("JdbcHelper.query(" + sql + "): " + e);
            }
            return null;
        }
    }
    
    
    public boolean update(String sql) {
        return this.update(sql, null);
    }
    
    public boolean update(String sql, StatementPreparer preparer) {
        try {
            this.openConnection();
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            this.applyPreparer(stmt, preparer);
            stmt.executeUpdate();

            stmt.close();

            return true;
            
        } catch (Exception e) {
            if (this.debugging) {
                System.out.println("JdbcHelper.update(" + sql + "): " + e);
            }
            return false;
        }
    }

    
    public Integer insert(String sql) {
        return this.insert(sql, null);
    }

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
            if (this.debugging) {
                System.out.println("JdbcHelper.insert(" + sql + "): " + e);
            }
            return null;
        }
    }

    
    private void applyPreparer(PreparedStatement stmt, StatementPreparer preparer) {
        if (preparer != null) {
            try {
                preparer.prepare(stmt);
            } catch (Exception e) {
                if (this.debugging) {
                    System.out.println("JdbcHelper.applyPreparer(): " + e);
                }
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
            if (this.debugging) {
                System.out.println("JdbcHelper.getGeneratedKey(): " + e);
            }
            return null;
        }

        return id;
    }
}
