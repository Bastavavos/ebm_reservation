package com.ebm.reservation.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .contentType("application/json")
                .content("{\"userId\":52,\"vehicleId\":1,\"startDate\":\"2025-01-01\",\"endDate\":\"2025-01-02\",\"kilometers\":10}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetByUserId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
