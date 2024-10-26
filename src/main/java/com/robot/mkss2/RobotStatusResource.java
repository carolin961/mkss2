package com.robot.mkss2;

import org.springframework.hateoas.RepresentationModel;
import java.util.List;

public class RobotStatusResource extends RepresentationModel<RobotStatusResource> {
    private final long id;
    private final Position position;
    private final int energy;
    private final List<String> inventory;

    public RobotStatusResource(Robot robot) {
        this.id = robot.getId();
        this.position = robot.getPosition();
        this.energy = robot.getEnergy();
        this.inventory = robot.getInventory();
    }

    public long getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public List<String> getInventory() {
        return inventory;
    }
}
