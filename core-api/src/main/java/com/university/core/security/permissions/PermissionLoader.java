package com.university.core.security.permissions;

import jakarta.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class PermissionLoader {

    private final Map<String, Set<String>> permissionMap = new HashMap<>();

    @PostConstruct
    public void loadMap() {
        ClassPathResource resource = new ClassPathResource("permissions.csv");

        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()//uses first row as header
                    .setSkipHeaderRecord(true)
                    .setTrim(true)
                    .build()
                    .parse(reader);

            for (CSVRecord r : records) {
                String role = r.get("ROLE");
                String method = r.get("HTTP_METHOD");
                String path = r.get("PATH");

                String key = method + ":" + path;

                permissionMap
                        .computeIfAbsent(role, x -> new HashSet<>())
                        .add(key);
            }


        } catch (IOException e) {
            throw new IllegalStateException("permissions.csv not found or unreadable", e);
        }
    }

    public boolean isAllowed(String role, String method, String path) {

        Set<String> permissions = permissionMap.getOrDefault(role, Set.of());

        for (String perm : permissions) {
            String[] parts = perm.split(":", 2);
            String permMethod = parts[0];
            String permPath = parts[1];

            if (!permMethod.equalsIgnoreCase(method)) {
                continue;
            }

            if (path.equals(permPath) || path.startsWith(permPath + "/")) {
                return true;
            }
        }

        return false;
    }

}
