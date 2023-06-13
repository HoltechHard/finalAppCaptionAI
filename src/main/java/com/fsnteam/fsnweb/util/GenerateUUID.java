package com.fsnteam.fsnweb.util;

import java.util.UUID;

public class GenerateUUID {
    public String generateUUID(){
        // Generate a UUID
        UUID uuid = UUID.randomUUID();

        // Get the current timestamp
        long timestamp = System.currentTimeMillis();

        // Create a filename using the UUID and timestamp
        String stringUUID = timestamp + "_" + uuid.toString();

        // Print the filename
        return stringUUID;
    }
}
