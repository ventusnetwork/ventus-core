package com.pyropoops.ventuscore.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public interface IDataObject {
    File getDataFile();

    FileConfiguration getDataObject();
}
