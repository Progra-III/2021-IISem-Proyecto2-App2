package model;

import controller.ServerController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerModel {

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

    private boolean verifyUser(String user, String password){                //????????????????????????????



    }

    public void Transaction(String id, String amount, String type){}

    public String returnId(String user){}

    public String typeTransaction(){}

    public void UpdateBalance(String amount, String user){}

    public String returnBalance(String user){}

    public void ChangePassword(String password, String user){}

    public String ReturnPassword(String user){}


}
