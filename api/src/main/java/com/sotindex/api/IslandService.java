package com.sotindex.api;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class IslandService {
    private final HashMap<String, Island> map = new HashMap<String, Island>();
    private final ObjectMapper mapper = new ObjectMapper();


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
     * Parse the JSON files in the given directory into {@code Island} objects and insert these objects into the map
     * @param dataDir The directory which contains the JSON files.
     * @return {@code false} if an exception occured, {@code true} otherwise
     */
    public boolean parseJSONFromDisk(File dataDir) {
        Island island;

        for (File f : dataDir.listFiles()) {
            if(isJSONFile(f)) {
                try {
                    island = mapper.readValue(f, Island.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                map.put(island.name().toLowerCase().replace(" ", ""), island);
            }
        }
        return true;

    }

    private boolean isJSONFile(File f) {
        String path = f.toString();
        int i = path.lastIndexOf(".");
        return f.isFile() && path.substring(i, path.length()).toLowerCase().equals(".json");
    }

}
