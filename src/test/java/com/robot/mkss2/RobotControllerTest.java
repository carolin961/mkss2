package com.robot.mkss2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RobotControllerTest {

    private static final Integer ROBOT_ID = 1;
    private static final Integer ITEM_ID = 2;
    private static final Integer TARGET_ID = 5;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RobotController robotController;

    @BeforeEach
    public void setup() throws Exception {
        Field service = RobotController.class.getDeclaredField("robotService");
        service.setAccessible(true);
        RobotService robotService = (RobotService) service.get(robotController);

        //Reset the DB
        robotService.robots = new HashMap<>();
        robotService.robots.put(ROBOT_ID, new Robot(ROBOT_ID, new Position(0, 0), 100));
        robotService.robots.put(TARGET_ID, new Robot(TARGET_ID, new Position(1, 0), 100));
    }

    // 1. Roboter Status abfragen
    @Test
    public void testRobotStatus() throws Exception {
        mockMvc.perform(get("/robot/" + ROBOT_ID + "/status"))
                .andExpect(status().isOk());
    }

    // 2. Roboter bewegen
    @Test
    public void testRobotMovementUp() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("direction","up");

        mockMvc.perform(post("/robot/" + ROBOT_ID + "/move")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRobotMovementDown() throws Exception{
        Map<String,Object> body = new HashMap<>();
        body.put("direction","down");

        mockMvc.perform(post("/robot/" + ROBOT_ID + "/move")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRobotMovementLeft() throws Exception{
        Map<String,Object> body = new HashMap<>();
        body.put("direction","left");

        mockMvc.perform(post("/robot/" + ROBOT_ID + "/move")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRobotMovementRight() throws Exception{
        Map<String,Object> body = new HashMap<>();
        body.put("direction","right");

        mockMvc.perform(post("/robot/" + ROBOT_ID + "/move")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // 3.1 Gegenstand mit der ID 2 aufheben
    @Test
    public void testPickupItem() throws Exception {
        mockMvc.perform(post("/robot/" + ROBOT_ID + "/pickup/" + ITEM_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventory[0]").value(2));
    }

    // 3.2 Item mit der ID 2 ablegen
    @Test
    public void testPutdownItem() throws Exception {
        //Pickup Item first
        mockMvc.perform(post("/robot/" + ROBOT_ID + "/pickup/" + ITEM_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventory[0]").value(2));

        mockMvc.perform(post("/robot/" + ROBOT_ID + "/putdown/" + ITEM_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventory").isEmpty());
    }

    // 4. Roboter Stauts aktualisieren (position & energy)
    @Test
    public void patchRobotPosition() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("energy",80);
        body.put("position",new Position(1, 1));

        mockMvc.perform(patch("/" + ROBOT_ID + "/state")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.energy").value(80))
                .andExpect(jsonPath("$.position.x").value(1))
                .andExpect(jsonPath("$.position.y").value(1));

    }

    // 5. Alle Aktionen des Roboters abrufen
    @Test
    public void testGetAllRobotAction() throws Exception {
        mockMvc.perform(get("/robot/" + ROBOT_ID + "/actions"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddAndRetrieveRobotActions() throws Exception {
        Map<String, Object> bodyMoveUp = new HashMap<>();
        bodyMoveUp.put("direction", "up");
        mockMvc.perform(post("/robot/" + ROBOT_ID + "/move")
                        .content(objectMapper.writeValueAsString(bodyMoveUp))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/robot/" + ROBOT_ID + "/pickup/" + ITEM_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventory[0]").value(ITEM_ID));

        String response = mockMvc.perform(get("/robot/" + ROBOT_ID + "/actions"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Actions Response JSON: " + response);
    }


    // 6. Anderen Roboter angreifen
    @Test
    public void testAttackRobot() throws Exception {
        mockMvc.perform(post("/robot/" + ROBOT_ID + "/attack/" + TARGET_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.energy").value(95));
    }
}
