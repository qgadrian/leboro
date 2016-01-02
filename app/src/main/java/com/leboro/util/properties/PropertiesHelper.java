package com.leboro.util.properties;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

}