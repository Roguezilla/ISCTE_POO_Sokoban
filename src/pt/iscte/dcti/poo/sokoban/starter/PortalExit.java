package pt.iscte.dcti.poo.sokoban.starter;

import pt.iul.ista.poo.utils.Point2D;

public class PortalExit extends SokobanObject implements ActiveObject {
    private PortalEntrance portalEntrance;

    public PortalExit(Point2D position, String imageName) {
        super(2, position, imageName);
    }

    public PortalEntrance getEntrance() {
        return this.portalEntrance;
    }

    public void link(PortalEntrance portalEntrance) {
        this.portalEntrance = portalEntrance;

        if (portalEntrance.getExit() == null) {
            portalEntrance.link(this);
        }
    }

    @Override
    public void interactWith(SokobanObject object) {
        if (Sokoban.getInstance().selectObject(sokobanObject -> sokobanObject.isAt(this.portalEntrance.position) && sokobanObject instanceof MovableObject) == null) {
            object.position = this.portalEntrance.position;
        }
    }
}
