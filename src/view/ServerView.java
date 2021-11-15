package view;

import controller.ServerController;
import javax.swing.*;

public class ServerView extends JFrame implements IView {

    //----------------------------------
    private JPanel windowPanel;
    private JTextArea txtArea;
    private JTextField txtField;
    private JScrollPane scrollPane;
    private ServerController serverController;

    //-----------------------------------

    public ServerView(){
        setContentPane(windowPanel);
        setTitle("Server Communication");
        setSize(650,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        manageTxtArea();
        manageScrollPane();
        manageTxtField();
    }

    public void manageTxtArea(){
        txtArea.setEditable(false);
        txtArea.setColumns(20);
        txtArea.setRows(5);
    }

    public void manageTxtField(){

    }

    public void manageScrollPane(){
        scrollPane.setViewportView(txtArea);
    }

    @Override
    public void addMessage(String message) {
        txtArea.append(message + "\n");
    }

    @Override
    public void setController(ServerController controller) {
        this.serverController = controller;
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
