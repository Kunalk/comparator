package com.waes.techinterview.comparator.integration;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * Created by Kunal on 08-11-2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @Before
    public void setup() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }
    @Test
    public void testInsertLeft() throws Exception{
        insertLeft(10L, "  \"dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ==\"");
    }

    @Test
    public void testInsertRight() throws Exception{
        insertRight(10L, "  \"dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ==\"");
    }

    @Test
    public void testDocumentContentsForInvalidDataFormat()throws Exception{
        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/"+1+"/right")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" + "  \"data\": " + "  \"dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ=\"" + "}"))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andReturn();
    }

    @Test
    public void testDocumentContentsForEquality()throws Exception{
        insertLeft(11L, "  \"dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ==\"");
        insertRight(11L, "  \"dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ==\"");

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/11").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comparatorResult", Matchers.is("EQUAL")));

    }

    @Test
    public void testDocumentContentsForLengthMismatch()throws Exception{
        insertLeft(12L, "  \"dGhpcyBpcyB3YWVzIHRlc3QgcHJvZ3JhbQ==\"");
        insertRight(12L, "  \"VGhpcyBpcyB3cm9uZyBpbnB1dA==\"");

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/12").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comparatorResult", Matchers.equalTo("LENGTH_MISMATCH")));

    }

    @Test
    public void testDocumentContentsForContentMismatch()throws Exception{
        insertLeft(13L, "  \"ZGl3YWxp\"");//diwali
        insertRight(13L, "  \"ZGl3YWxh\"");//diwala

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/13").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comparatorResult", Matchers.equalTo("OFFSET_MISMATCH")));

    }

    @Test
    public void testDocumentContentsForContentMismatchWithOffset()throws Exception{
        insertLeft(13L, "  \"ZGl3YWxp\"");//diwali
        insertRight(13L, "  \"ZGl3YWxh\"");//diwala

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/13").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.comparatorResult", Matchers.equalTo("OFFSET_MISMATCH")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.contentDifferences", Matchers.iterableWithSize(1)));


    }

    @Test
    public void testDocumentContentsWhenRecorNotPresent()throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/13")).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    public void testDocumentContentsWhenLeftRecorNotPresent()throws Exception{
        insertRight(13L, "  \"ZGl3YWxp\"");//diwali
        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/13")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDocumentContentsWhenRightRecorNotPresent()throws Exception{
        insertLeft(14L, "  \"ZGl3YWxp\"");//diwali
        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/14")).andExpect(MockMvcResultMatchers.status().isNotFound());
    }




    public void insertLeft(long id, String data) throws Exception{
        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/"+id+"/left")
                         .accept(MediaType.APPLICATION_JSON)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content("{\n" + "  \"data\": " + data+ "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }


    public void insertRight(long id,String data) throws Exception{
        mvc.perform(
                MockMvcRequestBuilders.post("/v1/diff/"+id+"/right")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" + "  \"data\": " + data + "}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }




}
