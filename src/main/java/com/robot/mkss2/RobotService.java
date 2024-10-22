package com.robot.mkss2;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RobotService {

    private Map<Integer, Robot> robot = new HashMap<>();

    public RobotService() {
        robot.put(1, new Robot(1, new Position(0, 0), 100));
        robot.put(2, new Robot(2, new Position(10, 10), 100));
    }

    public Robot getRobotById(Integer id) {
        return robot.get(id);
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

    public List<String> getRobotActions(Integer id) {
        return getRobotById(id).getActions();
    }

    public void attackRobot(Integer id, Integer targetId) {
        Robot attacker = getRobotById(id);
        Robot target = getRobotById(targetId);
        attacker.attack(target);
    }
}
