package ru.cft.tasks;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class FilesMerger<T extends Comparable<T>> {

    private final FilePool<T> filePool;

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public FilesMerger(FilePool<T> filePool) {
        this.filePool = filePool;
    }

    public boolean merge(Path outputPath) {
        T value = null;
        try(BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            while ((value = filePool.pop()) != null) {
                writer.write(value.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            logger.info("Error during writing value = " + value + " " +  e.getMessage());
            return false;
        }
        return true;
    }
}
