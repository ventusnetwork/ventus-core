package com.pyropoops.ventuscore.data;

import com.pyropoops.ventuscore.VentusCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataObject implements IDataObject {
    private static List<DataObject> dataObjects = new ArrayList<>();

    private String name;
    private String subfolder;
    private VentusCore plugin;
    private File file;
    private FileConfiguration config;

    public DataObject(String name, String subfolder){
        if(!name.toLowerCase().endsWith(".yml")){
            name += ".yml";
        }

        this.name = name;
        this.subfolder = subfolder;
        this.plugin = VentusCore.instance;

        try{
            this.loadConfig();
        }catch(IOException e){
            e.printStackTrace();
        }

        dataObjects.add(this);
    }

    public static void saveData(){
        for(DataObject dataObject : dataObjects){
            dataObject.saveConfig();
        }
    }

    private void loadConfig() throws IOException{
        this.file = subfolder != null ? new File(plugin.getDataFolder() + "/" + subfolder + "/" + name) :
                new File(plugin.getDataFolder() + "/" + name);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        this.config = YamlConfiguration.loadConfiguration(file);
        this.saveConfig();
    }

    private void saveConfig(){
        try{
            config.save(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public File getDataFile(){
        return file;
    }

    @Override
    public FileConfiguration getDataObject(){
        return config;
    }
}
