package ru.cft.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public abstract class FilePool<T extends Comparable<T>> {

    private final Map<BufferedReader, T> readers = new HashMap<>();

    private final Comparator<T> comparator;

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    public FilePool(Collection<Path> paths) {
        this(paths, Comparator.naturalOrder());
    }

    public FilePool(Collection<Path> paths, Comparator<T> comparator) {
        paths.forEach(path -> {
            try {
                final BufferedReader reader = Files.newBufferedReader(path);
                final T value = next(reader);

                if (value != null) {
                    readers.put(reader, value);
                } else {
                    reader.close();
                }
            } catch (IOException e) {
                logger.info("Error during file pool initialization " + e.getMessage());
            }
        });
        this.comparator = comparator;
    }

    public T pop() {
        if (readers.isEmpty()) {
            return null;
        }

        Map.Entry<BufferedReader, T> top = null;
        for (Map.Entry<BufferedReader, T> entry : readers.entrySet()) {
            if (top == null || comparator.compare(top.getValue(), entry.getValue()) > 0) {
                top = entry;
            }
        }

        final BufferedReader reader = top.getKey();
        final T value = top.getValue();

        T nextValue = next(reader);

        while (nextValue != null && comparator.compare(value, nextValue) > 0) {
            logger.info("Value = " + nextValue + " corrupts the order! It will be ignored");
            nextValue = next(reader);
        }

        if (nextValue != null) {
            readers.replace(reader, nextValue);
        } else {
            try {
                reader.close();
            } catch (IOException e) {
                logger.info("Error during closing resource " + e.getMessage());
            }
            readers.remove(top.getKey());
        }

        return value;
    }

    private T next(BufferedReader reader) {
        String line = null;
        try {
            if ((line = reader.readLine()) != null) {
                return cast(line);
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.info("Error during extracting value = " + line + " " + e.getMessage());
            return next(reader);
        } catch (NumberFormatException e) {
            logger.info("Error in format of value = " + line + " " + e.getMessage());
            return next(reader);
        }
    }

    abstract protected T cast(String value) throws NumberFormatException;
}
