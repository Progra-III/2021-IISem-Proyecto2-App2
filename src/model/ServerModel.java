package model;

import controller.ServerController;
import info.SQLExecutorURL;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerModel extends Thread {

    //--------------------------------

    Socket socket;
    ServerSocket serverSocket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    ServerController serverController;
    final int PUERTO = 7020;


    //---------------------------------

    public ServerModel(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public ServerModel(){}

    public void setServerController(ServerController serverController){
        this.serverController = serverController;
    }

    public void openPort() {
        try {
            serverSocket = new ServerSocket(PUERTO);

        } catch (IOException ex) {
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void waitClient() {
        try {
            socket = serverSocket.accept();
        } catch (IOException ex) {
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createData() {
        try {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

        } catch (IOException e) {
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void closeAll(){
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (socket != null) {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//----------------------------------------------------------------------------------------------------------\

    public void sendMessage(String message){
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException ex){
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String receiveUser(){
        try {
            String message = bufferedReader.readLine();
            return message;
        }catch (IOException ex){
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String receiveMessage(){
        try {
            String mensaje = bufferedReader.readLine();
            return mensaje;
        }catch (IOException ex){
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String receiveAmount(){
        try {
            String mensaje = bufferedReader.readLine();
            return mensaje;
        }catch (IOException ex){
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String receivePassword(){
        String message;
        try {
            message = bufferedReader.readLine();
            return message;
        }catch (IOException ex){
            Logger.getLogger(ServerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private boolean verifyUser(String user, String password){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        ResultSet resultSet = sqlExecutorURL.ejecutaSQL("SELECT * FROM CLIENTE");

        try {
            while (resultSet.next()){
                if (resultSet.getString("USUARIO").equals(user)&&resultSet.getString("CLAVE").equals(password))
                {
                    return true;
                }
            }
            return false;
        } catch (SQLException throwable) {
            sendMessage(throwable.getMessage());
        }
        return false;
    }

    public void Transaction(String id, String amount, String type){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();

        String[] query ={"INSERT INTO dbo.TRANSACCIONES (ID_CLIENTE,MONTO_TRANSAC,TIPO_ID) VALUES ('"+id+"','"+amount+"','"+type+"')"};
        sqlExecutorURL.prepareStatement(query);
    }

    public String returnId(String user){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        ResultSet rs = sqlExecutorURL.ejecutaSQL("SELECT CLIENTE.CEDULA FROM CLIENTE where CLIENTE.USUARIO = '"+user+"'");
        try {
            while (rs.next()){
                if (rs.getString("CEDULA") != null)
                {
                    return (rs.getString("CEDULA"));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return "0";
    }

    public String typeTransaction(){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        ResultSet rs = sqlExecutorURL.ejecutaSQL("SELECT TIPO_TRANSACCIONES.ID FROM TIPO_TRANSACCIONES where TIPO_TRANSACCIONES.TIPO_TRANSACCION = 'RETIRO'");
        try {
            while (rs.next()){
                if (rs.getString("ID") != null)
                {
                    return (rs.getString("ID"));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return "0";
    }

    public void UpdateBalance(String amount, String user){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        String[] query ={"UPDATE CLIENTE SET SALDO =? WHERE  CLIENTE.USUARIO = '"+user+"'",amount};
        sqlExecutorURL.prepareStatement(query);
    }

    public String returnBalance(String user){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        ResultSet rs = sqlExecutorURL.ejecutaSQL("SELECT CLIENTE.SALDO FROM CLIENTE where CLIENTE.USUARIO = '"+user+"'");
        try {

            while (rs.next()){
                if (rs.getString("SALDO") != null)
                {
                    return (rs.getString("SALDO"));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return "0";
    }

    public void ChangePassword(String password, String user){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        String[] query2 ={"UPDATE CLIENTE SET CLAVE =? WHERE CLIENTE.USUARIO = '"+user+"'",password};
        sqlExecutorURL.prepareStatement(query2);
    }

    public String ReturnPassword(String user){
        SQLExecutorURL sqlExecutorURL = new SQLExecutorURL("1433","BaseProyecto","sa","password");
        sqlExecutorURL.abreConexion();
        ResultSet rs = sqlExecutorURL.ejecutaSQL("SELECT CLIENTE.CLAVE FROM CLIENTE where CLIENTE.USUARIO = '"+user+"'");
        try {
            while (rs.next()){
                if (rs.getString("CLAVE") != null)
                {
                    return (rs.getString("CLAVE"));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return "0";

    }

    //----------------------------------------------------------------------------------------------------------

    @Override
    public void run() {
        while (true) {
            String message = receiveMessage();
            String user = receiveUser();
            String password = receivePassword();

            switch (message){
                case "login":

                    if (verifyUser(user, password)) {
                        sendMessage("Correcto");
                        serverController.addMessage("Se ha conectado: " + user);
                    } else {
                        sendMessage("Warning: Incorrecto");
                    }
                    break;

                case "saldo":
                    sendMessage(returnBalance(user));
                    break;

                case "retiro":

                    String amount = receiveAmount();
                    serverController.addMessage(amount);
                    UpdateBalance(amount, user);
                    sendMessage(returnBalance(user));

                    Transaction(returnId(user),amount,typeTransaction());
                    serverController.addMessage("Retiro completado!");
                    break;

                case "devuelve clave":
                    sendMessage(ReturnPassword(user));

                case "cambio clave":
                    ChangePassword(password, user);
                    ReturnPassword(user);
                    serverController.addMessage("Contraseña cambiada!");
                    break;
                case "saliendo":
                    serverController.exit();
                    break;
            }
        }
    }
}
