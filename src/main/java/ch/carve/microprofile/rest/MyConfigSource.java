package ch.carve.microprofile.rest;

import java.util.Collections;
import java.util.Map;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class MyConfigSource implements ConfigSource {

    @Override
    public Map<String, String> getProperties() {
        System.out.println("getProperties");
        return Collections.emptyMap();
    }

    @Override
    public String getValue(String propertyName) {
        System.out.println("getProperty: " + propertyName);
        if ("hello-uri".equals(propertyName)) {
            return "http://localhost:8080/hello";
        }
        return null;
    }

    @Override
    public String getName() {
        System.out.println("getName");
        return "MyConfigSource";
    }

    @Override
    public int getOrdinal() {
        return 120;
    }
}
