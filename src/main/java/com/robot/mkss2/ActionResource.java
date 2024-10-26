package com.robot.mkss2;

import org.springframework.hateoas.Link;
import java.util.List;

public class ActionResource {
    private final String action;
    private final String direction;
    private final String timestamp;
    private final List<Link> links;

    public ActionResource(String action, String direction, String timestamp, List<Link> links) {
        this.action = action;
        this.direction = direction;
        this.timestamp = timestamp;
        this.links = links;
    }

    public String getAction() { return action; }
    public String getDirection() { return direction; }
    public String getTimestamp() { return timestamp; }
    public List<Link> getLinks() { return links; }
}
