package mika32.Utils;

import mika32.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class Config {
    private final YamlConfiguration config;
    private final File file;

    public File getFile(){
        return file;
    }
    public YamlConfiguration getConfig(){
        return config;
    }

    public Config(){
        File dir = new File(System.getProperty("user.dir") + "/Entschuldigungen/Data");

        if(!dir.exists()){
            dir.mkdirs();
        }

        this.file = new File(dir, "config.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){

            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);

    }

    public void save(){
        try {
            config.save(file);
        }catch (IOException e){

        }
    }
}