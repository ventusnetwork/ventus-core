package com.pyropoops.ventuscore.data;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class DataObject implements IDataObject {
    public static Map<String, DataObject> dataObjects = new HashMap<>();

    private String path;
    private File dataFile;

    public DataObject(String path, boolean replace) {
        this.dataFile = new File(path);
        if (!path.endsWith(".json")) {
            path += ".json";
        }
        this.path = path;
        createDataFile(replace);
    }

    @Override
    public boolean createDataFile(boolean replace) {
        File file = new File(path);
        if (file.exists() && !replace) return true;
        try {
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        this.dataFile = file;
        return true;
    }

    @Override
    public File getDataFile() {
        return this.dataFile;
    }

    @Override
    public JSONObject getDataObject() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader(this.dataFile));
        } catch (ParseException | IOException e) {
            createDataFile(true);
            return getDataObject();
        }
        return jsonObject;
    }

    @Override
    public Object getValue(String key) {
        return this.getDataObject().get(key);
    }

    @Override
    public boolean saveFile(JSONObject jsonObject) {
        try {
            PrintWriter pw = new PrintWriter(this.dataFile);
            pw.write(jsonObject.toJSONString());

            pw.flush();
            pw.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
