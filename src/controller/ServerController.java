package controller;

import model.ServerModel;
import view.IView;
import view.ServerView;

public class ServerController {

    //--------------------------
    private ServerView serverView;
    private ServerModel serverModel;

    //---------------------------

    public ServerController(){
        this.serverView = new ServerView();
        this.serverModel = new ServerModel();

        serverModel.setServerController(this);
        serverView.setController(this);

        start();
    }

    private void start(){

        serverView.addMessage("Abriendo el puerto..");
        serverModel.openPort();
        serverView.addMessage("Esperando al cliente..");
        serverModel.waitClient();
        serverModel.createData();
        serverModel.start();
    }

    public void addMessage(String message) {
        serverView.addMessage(message);
    }

    public void exit() {
        serverModel.createData();
        serverView.exit();
    }

}
