package net.alureon.ircbutt.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static net.alureon.ircbutt.IRCbutt.log;

public class YAMLConfigurationFile {


    private final static File config = new File("config.yml");


    public YAMLConfigurationFile() {
        if (!config.exists()) {
            InputStream link = getClass().getResourceAsStream("config.yml");
            try {
                Files.copy(link, config.getAbsoluteFile().toPath());
            } catch (IOException ex) {
                log.error("Unable to copy configuration file: ", ex);
            }
        }
    }

}
