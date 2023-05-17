package org.zaripov.istore.product.conrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.zaripov.istore.product.dtos.PageDto;
import org.zaripov.istore.product.dtos.ProductDto;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findPriceBetween() throws Exception {
//        when
        BigDecimal min = BigDecimal.valueOf(2);
        BigDecimal max = BigDecimal.valueOf(3);
        MvcResult mvcResult = mockMvc.perform(get(String.format("/api/v1/find?minPrice=%s&maxPrice=%s", min, max))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        PageDto page = new ObjectMapper().readValue(json, PageDto.class);
//        then
        page.getItems().stream()
                .map(ProductDto::getPrice)
                .forEach(productPrice ->
                        assertTrue(productPrice.compareTo(max) <= 0 && productPrice.compareTo(min) >= 0));
    }

    @Test
    void findByTitle() throws Exception {
//        when
        String title = URLEncoder.encode("product #3", StandardCharsets.UTF_8);
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/find?titlePart=" + title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        PageDto page = new ObjectMapper().readValue(json, PageDto.class);
//        then
        page.getItems().stream()
                .map(ProductDto::getTitle)
                .forEach(t ->
                        assertTrue(t.toLowerCase().contains(t.toLowerCase())));
    }

    @Test
    void findByNotExistingTitle() throws Exception {
//        when
        String title = URLEncoder.encode("not existing title", StandardCharsets.UTF_8);
        mockMvc.perform(get("/api/v1/find?titlePart=" + title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items", hasSize(0)));
    }

    @Test
    void createOrUpdate() throws Exception {
//        given
        int sizeBefore = getProductSize();
        ProductDto product = ProductDto.builder()
                .categoryTitle("food")
                .title("new product")
                .build();
        String jsonProduct = (new ObjectMapper()).writeValueAsString(product);

//        when
        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProduct))
                .andExpect(status().isCreated());

//    then
        int sizeAfter = getProductSize();
        assertEquals(sizeAfter, sizeBefore + 1);

    }


    @Test
    void deleteById() throws Exception {
//        given
        int sizeBefore = getProductSize();
//        when
        mockMvc.perform(delete("/api/v1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//    then
        int sizeAfter = getProductSize();
        assertEquals(sizeAfter, sizeBefore - 1);
    }


    @Test
    void findById() throws Exception {
        mockMvc.perform(
                        get("/api/v1/1")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByNotExistingId() throws Exception {
        mockMvc.perform(
                        get("/api/v1/100500")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private int getProductSize() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/find")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        PageDto page = new ObjectMapper().readValue(json, PageDto.class);
        return page.getItems().size();
    }
}