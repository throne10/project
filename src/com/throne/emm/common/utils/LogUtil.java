package com.throne.emm.common.utils;

import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import android.os.Environment;


import de.mindpipe.android.logging.log4j.LogConfigurator;

public class LogUtil {
    public static Logger logInstance(String name) {
        return Logger.getLogger(name);
    }
    public static void LogCofig() {
        LogConfigurator logConfigurator = new LogConfigurator();

        logConfigurator.setFileName(Environment.getExternalStorageDirectory()

                + File.separator + "Throne" + File.separator + "logs"

        + File.separator + "log4j.txt");

        logConfigurator.setRootLevel(Level.DEBUG);

        logConfigurator.setLevel("org.apache", Level.ERROR);

        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");

        logConfigurator.setMaxFileSize(1024 * 1024 * 5);

        logConfigurator.setImmediateFlush(true);

        logConfigurator.configure();
    }
}
