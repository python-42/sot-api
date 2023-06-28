package com.sotindex.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class MaintainController {
    private final IslandService service = IslandService.getInstance();
    private final Logger logger = LoggerFactory.getLogger(MaintainController.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final String ENDPOINT_URL = "/maintain/";

    //TODO require some form of authentication

    @PostMapping(ENDPOINT_URL + "reload_json")
    public JsonNode reloadJSON(HttpServletResponse response) throws JsonMappingException, JsonProcessingException {
        if(service.parseJSONFromDisk()) {
            logger.info("JSON reload called. JSON parsed successfully.");
            response.setStatus(HttpServletResponse.SC_OK);
            return mapper.readTree(
                "{\"action\" : \"reload_json\", \"status\" : \"OK\"}"
            );
        }else {
            logger.error("JSON relaod called. JSON parsing failed.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return mapper.readTree(
                "{\"action\" : \"reload_json\", \"status\" : \"FAIL\"}"
            );
        }
    }

    @PostMapping(ENDPOINT_URL + "shutdown")
    public void shutdownServer() throws JsonMappingException, JsonProcessingException {
        logger.info("Shutdown endpoint called. Shutting down...");
        ApiApplication.exitApplication(0);
    }
}
