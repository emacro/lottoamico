/*
 * i-service 5
 *
 * Copyright (c) 1999-2007 Ingenium Technology Srl
 * All rights reserved.
 *
 * Proprietary and confidential of Ingenium Technology Srl
 * Use is subject to license terms.
 */
package it.emacro.extractor.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Marco Ghezzi
 */
public class PropertyLoader {

    public static Properties getProperties(String path) throws Exception {
        Properties properties;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(path);
            properties = new Properties();
            properties.load(fin);
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                	e.printStackTrace();
                    // LogManager.getInstance().error(e);
                }
            }
        }
        return properties;
    }

    public static Properties getPropertiesOrEmpty(String path) {
        Properties properties;
        try {
            properties = getProperties(path);
        } catch (Exception e) {
            properties = new Properties();
            e.printStackTrace();
            // LogManager.getInstance().error(e);
        }
        return properties;
    }

}
