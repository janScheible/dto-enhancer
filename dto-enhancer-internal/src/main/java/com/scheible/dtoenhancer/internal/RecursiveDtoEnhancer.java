package com.scheible.dtoenhancer.internal;

import com.scheible.dtoenhancer.api.Config;
import com.scheible.dtoenhancer.api.DtoEnhancer;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author sj
 */
public class RecursiveDtoEnhancer implements DtoEnhancer {

    @Override
    public boolean enhance(File baseDir, Config config) {
        AtomicBoolean changed = new AtomicBoolean(false);
        enhanceRecursively(baseDir.toPath(), config, changed);
        return changed.get();
    }

    private void enhanceRecursively(Path dir, Config config, AtomicBoolean changed) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                File current = path.toFile();
                if (path.toFile().isDirectory()) {
                    enhanceRecursively(path, config, changed);
                } else if (current.getName().toLowerCase().endsWith(".java")) {
                    if (SingleDtoEnhancer.enhance(current, config)) {
                        changed.set(true);
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
