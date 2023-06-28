package com.sotindex.api;

import java.io.File;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;;

@RestController
public class ApiController {

    //@Value("${islandDataPath}")
    private String dataDir = "api/src/main/resources/static/islands";

    private final String ENDPOINT_URL = "/api/";
    private final IslandService service = new IslandService();;

    private final Logger logger = LoggerFactory.getLogger(ApiController.class);

    public ApiController() {
        if(!service.parseJSONFromDisk(new File(dataDir))) {
            logger.error("An exception occurred while parsing JSON from the disk. Server exiting...");
            ApiApplication.exitApplication(1);
            
        }else {
            logger.info("JSON parsed successfully.");
        }
    }

    @RequestMapping("**")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String catchall(){
        return "I am a teapot.";
    }

    @RequestMapping(ENDPOINT_URL + "get_single")
    public Island getIsland(@RequestParam(name="name") String name) {
        name = name.toLowerCase().replace(" ", "").replace("-", "").replace("_", "");
        if(service.islandExists(name)) {
            return service.getIsland(name);
        }
        return null;
    }

    @RequestMapping(ENDPOINT_URL + "get_all")
    public Collection<Island> getIslands(){
        return service.getIslands();
    }
}
