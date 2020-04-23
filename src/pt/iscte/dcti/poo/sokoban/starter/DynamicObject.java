package pt.iscte.dcti.poo.sokoban.starter;

//implemented by objects that can change their moveability during the game
public interface DynamicObject {
    void setMovability(boolean state);
    boolean canMove();
}
