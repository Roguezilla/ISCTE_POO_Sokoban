package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class Portal extends SokobanObject implements ActiveObject {
    private Portal link;

    public Portal(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    public Portal getLink() {
        return this.link;
    }

    public void link(Portal link) {
        this.link = link;

        if (link.getLink() == null) {
            link.link(this);
        }
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(this.link.getPosition()) && sokobanObject instanceof MovableObject) == null) {
            object.setPosition(this.link.getPosition());
        }
    }
}
