package com.example.fairpaysystem.service.utils;

import com.example.fairpaysystem.activepayrecord.ActivePaySystemConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DirectoryManager
{


    // log4j v2 log
    private static final Logger log = LogManager.getLogger(DirectoryManager.class);

    private String rootFolder;

    private String fileName;

    private File[] folderStructure;

    private String fullPathFileName;

    public DirectoryManager(ActivePaySystemConfig activePaySystemConfig) throws IOException
    {
        this.rootFolder = activePaySystemConfig.getHome();
        this.fileName = activePaySystemConfig.getActiveFile();
        this.createDirectories(rootFolder);
        this.fullPathFileName = rootFolder + "\\" + fileName;

    }

    private boolean createActivePaySystemFile(String root) throws IOException
    {


            createDirectories(root + "\\spglobal_policies\\ags_endpoints\\prod");
            addOptionalFiles(new File(root + "\\spglobal_policies\\ags_endpoints\\prod"));

            createDirectories(root + "\\spglobal_policies\\ags_endpoints\\preprod");
            addOptionalFiles(new File(root + "\\spglobal_policies\\ags_endpoints\\preprod"));


        setFolderStructure(new File(root + "\\spglobal_policies\\ags_endpoints").listFiles());

        return  true;
    }


    public boolean doesFileExist(String path) {
        return Files.exists(Paths.get(path));
    }

    private void addOptionalFiles(File directory) throws IOException
    {

        if (directory.isDirectory()) {
            File[] subDirectories = directory.listFiles(File::isDirectory);
            if (subDirectories != null) {
                for (File subDirectory : subDirectories) {

                    // add valid "empty" json content to each file

                    writeFile(subDirectory.getAbsolutePath() +
                                    "\\arcgis-public\\extensions.json",
                            "{\"extensions\": {}}");
                    writeFile(subDirectory.getAbsolutePath() +
                                    "\\arcgis-public\\properties.json",
                            "{\"properties\": {}}");
                    writeFile(subDirectory.getAbsolutePath() +
                                    "\\arcgis-public\\restrictions.json",
                            "{\"restrictions\": {}}");
                }
            }
        } else {
            System.err.println("ERROR: " + directory + " does not exist");
            System.exit(2);
        }
    }

    private void createDirectories(String path) {

        new File(path).mkdirs();

    }

    private void createFile(String path) {
        try {
            new File(path).createNewFile();
        } catch (IOException e) {
            System.err.println("ERROR: File " + path + " could not be created");
            System.exit(3);
        }
    }


    public void writeFile(String path, String content) throws IOException
    {

        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }


    public File[] getFolderStructure()
    {
        return folderStructure;
    }

    public void setFolderStructure(File[] folderStructure)
    {
        this.folderStructure = folderStructure;
    }

    public String getRootFolder()
    {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder)
    {
        this.rootFolder = rootFolder;
    }

    public String getFullPathFileName() {
        return fullPathFileName;
    }

    public void setFullPathFileName(String fullPathFileName) {
        this.fullPathFileName = fullPathFileName;
    }
}

