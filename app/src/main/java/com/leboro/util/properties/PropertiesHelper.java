package com.leboro.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.leboro.MainActivity;

import android.content.Context;
import android.content.res.AssetManager;

public class PropertiesHelper {

    public static Properties getProperties(Context context, String propertiesFilename) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = assetManager.open(propertiesFilename);
            properties.load(inputStream);

            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot access properties file");
        }
    }

    public static String getProperty(String propertyName) {
        return MainActivity.properties.getProperty(propertyName);
    }

    public static Long getPropertyAsLong(String propertyName) {
        return Long.valueOf(MainActivity.properties.getProperty(propertyName));
    }

    public static String getSecretProperty(String propertyName) {
        return MainActivity.secretProperties.getProperty(propertyName);
    }

}