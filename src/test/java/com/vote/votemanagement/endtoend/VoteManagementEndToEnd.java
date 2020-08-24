package com.vote.votemanagement.endtoend;


import com.vote.votemanagement.enumerator.ErrorMessages;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@Sql({"/sql/purge.sql", "/sql/seed.sql"})
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class VoteManagementEndToEnd {

    private static final String BASE_ENDPOINT = "http://localhost:8090/vote-management";
    private static final String ASSOCIATE_ENDPOINT = "/associate";
    private static final String ASSEMBLY_ENDPOINT = "/assembly";

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static String readJson(String filename) {
        try {
            return FileUtils.readFileToString(ResourceUtils.getFile("classpath:" + filename), "UTF-8");
        } catch (IOException exception) {
            return null;
        }
    }

    private HttpHeaders buildHttpHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    public void shouldReturn200WhenInsertNewAssociateRegistryWithSuccess() {
        final String payload = readJson("request/insertAssociateWithSuccess.json");
        final HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSOCIATE_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("{\"cpf\":\"49751616131\",\"name\":\"otto\",\"creationDate\":"));
    }

    @Test
    public void shouldReturn400WhenInsertNewAssociateWithCPFDuplicated() {
        final String payload = readJson("request/duplicatedAssociateCPF.json");
        final HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSOCIATE_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains( ErrorMessages.ASSOCIATE_ALREADY_EXIST.getMessage()));
    }

    @Test
    public void shouldReturn200WhenGettAllAssociates() {
        final HttpEntity<String> entity = new HttpEntity<String>(buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSOCIATE_ENDPOINT), HttpMethod.GET, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldReturn200WhenCreateNewAssemblyWithSuccess() {
        final String payload = readJson("request/createAssemblyWithSuccess.json");
        final HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSEMBLY_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("\"name\":\"Tiburcio Assembly\",\"votingSession\":null,\"createdBy\":{\"cpf\":\"32025468300\",\"name\":\"Tiburcio\",\"creationDate\":null},\"creationDate\":"));
    }

    @Test
    public void shouldReturn400WhenCreateAssemblyWithRepeatedName() {
        final String payload = readJson("request/assemblyWithRepeatedName.json");
        final HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSEMBLY_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains( ErrorMessages.ASSEMBLY_NAME_ALREADY_EXIST.getMessage()));
    }

    @Test
    public void shouldReturn400WhenCreateAssemblyWithAssociateNotExist() {
        final String payload = readJson("request/createAssemblyWithAssociateNoteExist.json");
        final HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSEMBLY_ENDPOINT), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains( ErrorMessages.ASSOCIATE_NOT_FOUND.getMessage()));
    }

    @Test
    public void shouldReturn200WhenOpenVotingSession() {
        final String payload = readJson("request/openVotingSessionWithSuccess.json");
        final HttpEntity<String> entity = new HttpEntity<String>(payload, buildHttpHeaders());
        final ResponseEntity<String> response = testRestTemplate.exchange(BASE_ENDPOINT.concat(ASSEMBLY_ENDPOINT).concat("/OpenVotingSession"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}