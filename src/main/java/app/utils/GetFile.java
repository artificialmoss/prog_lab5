package app.utils;

import app.exceptions.NoFileException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface GetFile {
    default boolean getReadableFile(String filename) throws NoFileException {
        try {
            Path path = Paths.get(filename);
            if (!Files.exists(path) | Files.isDirectory(path) | !Files.isReadable(path)) {
                throw new NoFileException("This file doesn't exist or can't be accessed.");
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    default File getWritableFile(String filename, String defaultName) throws NoFileException {
        Path path = Paths.get(filename);
        if (Files.exists(path) && (Files.isDirectory(path) | !Files.isWritable(path))) {
            Path defaultPath = Paths.get(defaultName);
            if (Files.exists(defaultPath) && (Files.isDirectory(defaultPath) | !Files.isWritable(defaultPath))) {
                throw new NoFileException("Can't create a writable file with this name.");
            } else {
                File file = new File(defaultName);
                try {
                    file.getAbsoluteFile().getParentFile().mkdirs();
                    file.createNewFile();
                    return file;
                } catch (IOException e) {
                    System.out.println("get1");
                    throw new NoFileException("Can't create a writable file with this name.");
                }
            }
        } else {
            File file = new File(filename);
            try {
                file.getAbsoluteFile().getParentFile().mkdirs();
                file.createNewFile();
                return file;
            } catch (IOException e) {
                throw new NoFileException("Can't create a writable file with this name.");
            }
        }
    }
}
