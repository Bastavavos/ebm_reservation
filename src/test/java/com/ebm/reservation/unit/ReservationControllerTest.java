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
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/reservations/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    public void testCreate() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
//                .contentType("application/json")
//                .content("{\"user_id\":8,\"vehicle_id\":1,\"startDate\":\"2023-01-01\",\"endDate\":\"2023-01-02\",\"kilometers\":100,\"res_price\":100.0}"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    @Test
    public void testUpdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/reservations/1")
                .contentType("application/json")
                .content("{\"user_id\":8,\"vehicle_id\":1,\"startDate\":\"2023-01-01\",\"endDate\":\"2023-01-02\",\"kilometers\":100,\"res_price\":100.0}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
