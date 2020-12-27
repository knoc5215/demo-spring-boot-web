package me.jumen.demospringbootweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.oxm.Marshaller;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest // 슬라이싱 테스트 -> web과 관련된 bean들만 넣어주므로, formatter와 같은 것들은 테스트 불가
@SpringBootTest // 모든 bean들을 다 등록하는 통합테스트 + 이 경우에는  @AutoConfigureMockMvc를 수동으로 넣어줘야 함
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        this.mockMvc.perform(get("/hello/jumen"))
                .andDo(print())
                .andExpect(content().string("hello jumen"));
    }

    @Test
    public void helloRequestParam() throws Exception {
        mockMvc.perform(get("/helloRequestParam").param("name", "jumen"))
                .andDo(print())
                .andExpect(content().string("hello jumen"));
    }

    @Autowired
    PersonRepository personRepository;

    @Test
    public void jpaTest() throws Exception {
        Person person = new Person();
        person.setName("jumen");
        Person savedPerson = personRepository.save(person);

        this.mockMvc.perform(get("/jpaTest").param("id", savedPerson.getId().toString()))
                .andDo(print())
                .andExpect(content().string("hello jumen"));
    }

    /*resoucre handler test*/
    @Test
    public void index() throws Exception {
        this.mockMvc.perform(get("/mobile/index.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello Mobile")))
                .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));

    }

    @Test
    public void stringMessage() throws Exception {
        this.mockMvc.perform(get("/message")
                .content("hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));

    }

    /* HTTP Message Converter : JSON */

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void jsonMessage() throws Exception {
        Person person = new Person();
        person.setId(2020L);
        person.setName("JUMEN");

        String jsonString = objectMapper.writeValueAsString(person);

        this.mockMvc.perform(get("/jsonMessage")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2020))
                .andExpect(jsonPath("$.name").value("JUMEN"));

    }

    /* HTTP Message Converter : XML */

    @Autowired
    Marshaller marshaller;

    @Test
    public void xmlMessage() throws Exception {
        Person person = new Person();
        person.setId(2020L);
        person.setName("JUMEN");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();


        this.mockMvc.perform(get("/xmlMessage")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/id").string("2020"))
                .andExpect(xpath("person/name").string("JUMEN"))
        ;


    }
}