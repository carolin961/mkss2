package com.robot.mkss2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RobotController {

    @Autowired
    private RobotService robotService;

    // 1. Roboter-Status abrufen
    @GetMapping("/robot/{id}/status")
    ResponseEntity<Robot> getStatus(@PathVariable int id) {
        Robot robot = robotService.getRobotById(id);
        return (robot == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(robot, HttpStatus.OK);
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
    ResponseEntity<Robot> updateState(@PathVariable int id, @RequestBody Map<String, Object> payload) {
        robotService.updateState(id, payload);
        return  new ResponseEntity<>(robotService.getRobotById(id), HttpStatus.OK);
    }

    // 5. Alle Aktionen des Roboters abrufen
    @GetMapping("/robot/{id}/actions")
    ResponseEntity<PaginatedActionsResource> getActions(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        PaginatedActionsResource resource = robotService.getPaginatedActions(id, page, size);
        if (resource == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        resource.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RobotController.class).getActions(id, page, size)).withSelfRel());

        if (resource.getPage().isHasNext()) {
            resource.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RobotController.class).getActions(id, page + 1, size)).withRel("next"));
        }

        if (resource.getPage().isHasPrevious()) {
            resource.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RobotController.class).getActions(id, page - 1, size)).withRel("previous"));
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    // 6.Anderen Roboter angreifen
    @PostMapping("/robot/{id}/attack/{targetId}")
    ResponseEntity<String> attackRobot(@PathVariable int id, @PathVariable int targetId) {
        robotService.attackRobot(id, targetId);
        return new ResponseEntity(robotService.getRobotById(id), HttpStatus.OK);
    }
}

