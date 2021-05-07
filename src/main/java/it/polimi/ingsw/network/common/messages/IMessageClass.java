package it.polimi.ingsw.network.common;

public interface IMessageClass {

    public String getCode();
    public String addBody(String body);
    public boolean check(String message);
    public boolean check(String arguments, String message);
}
