package info;

import java.sql.*;

public class SQLExecutorURL {

    //---------------------------------------------------------------------------------
    private String sqlServerConnectionURL;
    private Connection dbConn = null;
    private PreparedStatement stmt = null;  //Instruccion de ejemplo de instruccion (SELECT * FROM NOMBRE_TABLA WHERE CAMPO1 =1)
    private ResultSet rs= null;

    private String port;
    private String dbName;
    private String user;
    private String pwd;

    //---------------------------------------------------------------------------------

    public SQLExecutorURL(String port, String dbName, String user, String pwd){
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.pwd = pwd;
        sqlServerConnectionURL = "jdbc:sqlserver://localhost:"+port+";databaseName="+
                dbName+";user="+user+";password="+pwd;
    }

    public void abreConexion(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            dbConn = DriverManager.getConnection(sqlServerConnectionURL);
        }catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    public void prepareStatement(String[] parametros){
        try {
            stmt = dbConn.prepareStatement(parametros[0]);
            for (int i = 1; i<parametros.length; i++){
                stmt.setString(i,parametros[i]);
            }
            stmt.executeUpdate();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ResultSet ejecutaSQL(String sql){
        try {
            stmt = dbConn.prepareStatement(sql);
            rs = stmt.executeQuery();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return rs;
    }


    public void cierraConexion(){
        if(rs != null){
            try {
                rs.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        if(dbConn != null){
            try {
                dbConn.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }

}
