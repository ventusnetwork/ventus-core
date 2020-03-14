package com.pyropoops.ventuscore.data;

import org.json.simple.JSONObject;

import java.io.File;

public interface IDataObject {

    boolean createDataFile(boolean replace);

    File getDataFile();

    JSONObject getDataObject();

    Object getValue(String key);

    boolean saveFile(JSONObject jsonObject);
}
