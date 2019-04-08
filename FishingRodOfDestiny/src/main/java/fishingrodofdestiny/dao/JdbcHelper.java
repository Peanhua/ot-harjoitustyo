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

    public JdbcHelper(String databaseUrl) {
        this.databaseUrl   = databaseUrl;
        try {
            this.rowSetFactory = RowSetProvider.newFactory();
        } catch (Exception e) {
            this.rowSetFactory = null;
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
            Connection connection = DriverManager.getConnection(this.databaseUrl);

            PreparedStatement stmt = connection.prepareStatement(sql);
            this.applyPreparer(stmt, preparer);
            ResultSet rs = stmt.executeQuery();

            CachedRowSet rv = this.rowSetFactory.createCachedRowSet();
            rv.populate(rs);

            stmt.close();
            rs.close();
            connection.close();
            
            return rv;
            
        } catch (Exception e) {
            System.out.println("JdbcHelper.query(" + sql + "): " + e);
            return null;
        }
    }
    
    
    public boolean update(String sql) {
        return this.update(sql, null);
    }
    
    public boolean update(String sql, StatementPreparer preparer) {
        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl);
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            this.applyPreparer(stmt, preparer);
            stmt.executeUpdate();

            stmt.close();
            connection.close();

            return true;
            
        } catch (Exception e) {
            System.out.println("JdbcHelper.update(" + sql + "): " + e);
            return false;
        }
    }

    
    public Integer insert(String sql) {
        return this.insert(sql, null);
    }

    public Integer insert(String sql, StatementPreparer preparer) {
        try {
            Connection connection = DriverManager.getConnection(this.databaseUrl);
            
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            this.applyPreparer(stmt, preparer);
            stmt.executeUpdate();

            Integer id = this.getGeneratedKey(stmt);
            
            stmt.close();
            connection.close();

            return id;
            
        } catch (Exception e) {
            System.out.println("JdbcHelper.insert(" + sql + "): " + e);
            return null;
        }
    }

    
    private void applyPreparer(PreparedStatement stmt, StatementPreparer preparer) {
        if (preparer != null) {
            preparer.prepare(stmt);
        }
    }

        
    private Integer getGeneratedKey(PreparedStatement stmt) {
        Integer id = null;
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        generatedKeys.close();
        return id;
    }
}
