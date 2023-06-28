package com.sotindex.api;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IslandService {
    private HashMap<String, Island> map = new HashMap<String, Island>();
    private final ObjectMapper mapper = new ObjectMapper();
    
    //@Value("${islandDataPath}")
    private String dataDir = "api/src/main/resources/static/islands";


    private static IslandService instance;

    public static IslandService getInstance() {
        if(instance == null) {
            instance = new IslandService();
        }
        return instance;
    }

    private IslandService(){}


    /**
     * Get the given island. Note that the island names do not include any spaces or puncuation. 
     * @param name of the island
     * @return {@code Island} object which corresponds to this name or {@code null}
     */
    public Island getIsland(String name) {
        return map.get(name);
    }

    /**
     * Check if the given island exists. Note that the island names do not include any spaces or puncuation. 
     * @param name of the island
     * @return {@code true} if this island exists, {@code false} otherwise
     */
    public boolean islandExists(String name) {
        return map.containsKey(name);
    }

    public Collection<Island> getIslands() {
        return map.values();
    }

    /**
     * Parse the JSON files in the given directory into {@code Island} objects and insert these objects into the a map, replacing the old map entirely. 
     * If parsing fails, the new map does not overwrite the old map (the values are not changed at all).
     * @param dataDir The directory which contains the JSON files.
     * @return {@code false} if an exception occured, {@code true} otherwise
     */
    public boolean parseJSONFromDisk(File dataDir) {
        Island island;
        HashMap<String, Island> newMap = new HashMap<String, Island>();

        for (File f : dataDir.listFiles()) {
            if(isJSONFile(f)) {
                try {
                    island = mapper.readValue(f, Island.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                newMap.put(island.name().toLowerCase().replace(" ", ""), island);
            }
        }
        map = newMap;
        return true;

    }

    /**
     * Parse JSON from the default path specified in the properties file. See {@code parseJSONFromDisk(File)} for more details
     */
    public boolean parseJSONFromDisk() {
        return parseJSONFromDisk(new File(dataDir));
    }

    private boolean isJSONFile(File f) {
        String path = f.toString();
        int i = path.lastIndexOf(".");
        return f.isFile() && path.substring(i, path.length()).toLowerCase().equals(".json");
    }

}
