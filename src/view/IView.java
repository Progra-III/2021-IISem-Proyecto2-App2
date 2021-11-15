package view;

import controller.*;

public interface IView {

    public void addMessage(String message);

    public void setController(ServerController controller);

    void exit();

}
