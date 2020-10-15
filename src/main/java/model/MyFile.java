package model;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyFile extends File {
    private String generationDateTime;
    private String sizeStr;

    public MyFile(Path path) {
        super(path.toString());
        generationDateTime = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss")
                .format(path.toFile().lastModified());
        sizeStr = path.toFile().length() + " B";
    }

    public String getGenerationDateTime(){
        return generationDateTime;
    }

    public String getSize(){
        return sizeStr;
    }

    public List<MyFile> getListFiles(Path rootDir) throws IOException {
        List<MyFile> listFiles = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(rootDir))
        {
            for (Path path : files) {
                MyFile file = new MyFile(path);
                listFiles.add(file);
            }
        }
        return listFiles;
    }
}
