package com.robot.mkss2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class RobotController {

    @Autowired
    private RobotService robotService;

    /*private static long sequence = 1;
    private static long nextValue()
    {
        return sequence++;
    }
    private final Map<Long, Robot> table;

    RobotController() {

        table = new ConcurrentHashMap<>();
        long id1 = nextValue();
        table.put(id1, new Robot(id1, new Position(1,1), 3473));
        long id2 = nextValue();
        table.put(id2, new Robot(id2, new Position(2,2), 6956));
        long id3 = nextValue();
        table.put(id3, new Robot(id3, new Position(3,3), 8999));

    }*/

    // 1. Roboter-Status abrufen
    @GetMapping("/robot/{id}/status")
    ResponseEntity<Robot> getStatus(@PathVariable int id) {
        Robot robot = robotService.getRobotById(id);
        if(robot == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(robotService.getRobotById(id), HttpStatus.OK);
    }

    // 2. Roboter bewegen
    @PostMapping("/robot/{id}/move")
    ResponseEntity<Robot> move(@PathVariable int id, @RequestBody Map<String, String> payload) {
        String direction = payload.get("direction");
        robotService.moveRobot(id, direction);
        return new ResponseEntity<>(robotService.getRobotById(id), HttpStatus.OK);
    }

    // 3.1 Gegenstand aufheben
    @PostMapping("/robot/{id}/pickup/{itemId}")
    ResponseEntity<Robot> pickup(@PathVariable int id, @PathVariable int itemId) {
        robotService.pickUpItem(id, itemId);
        return new ResponseEntity<>(robotService.getRobotById(id), HttpStatus.OK);
    }

    // 3.2 Gegenstand ablegen
    @PostMapping("/robot/{id}/putdown/{itemId}")
    ResponseEntity<Robot> putDown(@PathVariable int id, @PathVariable int itemId) {
        robotService.putDownItem(id, itemId);
        return new ResponseEntity<>(robotService.getRobotById(id), HttpStatus.OK);
    }

    // 4. Roboter-Zustand aktualisieren
    @PatchMapping("/{id}/state")
    ResponseEntity<String> updateState(@PathVariable int id, @RequestBody Map<String, Object> payload) {
        robotService.updateState(id, payload);
        return ResponseEntity.ok("Robot state updated.");
    }

    // 5. Alle Aktionen des Roboters abrufen
    @GetMapping("/robot/{id}/actions")
    ResponseEntity<String> getActions(@PathVariable int id) {
        robotService.getRobotActions(id);
        return new ResponseEntity(robotService.getRobotActions(id), HttpStatus.OK);
    }

    // 6.Anderen Roboter angreifen
    @PostMapping("/robot/{id}/attack/{targetId}")
    ResponseEntity<String> attackRobot(@PathVariable int id, @PathVariable int targetId) {
        robotService.attackRobot(id, targetId);
        return new ResponseEntity(robotService.getRobotById(id), HttpStatus.OK);
    }
}

