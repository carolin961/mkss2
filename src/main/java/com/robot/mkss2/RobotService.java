package com.robot.mkss2;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RobotService {

    public Map<Integer, Robot> robots = new HashMap<>();

    public RobotService() {
        robots.put(1, new Robot(1, new Position(0, 0), 100));
        robots.put(2, new Robot(2, new Position(10, 10), 100));
    }

    public Robot getRobotById(Integer id) {
        return robots.get(id);
    }

    public void moveRobot(Integer id, String direction) {
        Robot robot = getRobotById(id);
        robot.move(direction);
    }

    public void pickUpItem(Integer id, Integer itemId) {
        Robot robot = getRobotById(id);
        robot.pickUpItem(itemId);
    }

    public void putDownItem(Integer id, Integer itemId) {
        Robot robot = getRobotById(id);
        robot.putDownItem(itemId);
    }

    public void updateState(Integer id, Map<String, Object> payload) {
        Robot robot = getRobotById(id);
        if (payload.containsKey("energy")) {
            robot.setEnergy((Integer) payload.get("energy"));
        }
        if (payload.containsKey("position")) {
            Map<String, Integer> position = (Map<String, Integer>) payload.get("position");
            robot.setPosition(new Position(position.get("x"), position.get("y")));
        }
    }

    public PaginatedActionsResource getPaginatedActions(int id, int page, int size) {
        Robot robot = getRobotById(id);
        List<String> actions = robot.getActions();
        int totalElements = actions.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, totalElements);

        List<ActionResource> actionResources = actions.subList(start, end).stream()
                .map(action -> new ActionResource(
                        action,
                        "up",
                        "2024-10-17T10:00:00Z",
                        List.of(Link.of("/robot/" + id + "/actions/" + actions.indexOf(action)).withSelfRel())
                ))
                .collect(Collectors.toList());

        PageMetadata pageMetadata = new PageMetadata(page, size, totalElements, totalPages);

        return new PaginatedActionsResource(actionResources, pageMetadata);
    }

    public void attackRobot(Integer id, Integer targetId) {
        Robot attacker = getRobotById(id);
        Robot target = getRobotById(targetId);
        attacker.attack(target);
    }
}
