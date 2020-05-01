package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class PortalEntrance extends SokobanObject implements ActiveObject {
    private PortalExit portalExit;

    public PortalEntrance(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    public PortalExit getExit() {
        return this.portalExit;
    }

    public void link(PortalExit portalExit) {
        this.portalExit = portalExit;
        if (portalExit.getEntrance() == null) {
            portalExit.link(this);
        }
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(this.portalExit.position) && sokobanObject instanceof MovableObject) == null)
            object.position = this.portalExit.position;
    }
}
