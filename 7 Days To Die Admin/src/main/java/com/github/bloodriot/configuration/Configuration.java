package com.github.bloodriot.configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public final class Configuration {
    private Properties properties = null;

    public Configuration() {
    }

    public void writeSettings() {
        OutputStream output = null;
        try {
            output = new FileOutputStream("config.properties");
            properties.store(output, "");
        } catch (IOException error) {
            // Unable to create the properties file.
            error.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
        }
    }

    private void loadSettings() {
        properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");
            properties.load(input);
        } catch (IOException error) {
            // Unable to create the properties file.
            error.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException error) {
                    error.printStackTrace();
                }
            }
        }
    }

    public String getProperty(String name) {
        if (properties == null) {
            loadSettings();
        }
        return properties.getProperty(name, null);
    }

    public void setProperty(String key, String value) {
        if (properties == null) {
            loadSettings();
        }
        properties.setProperty(key, value);
        writeSettings();
    }
}