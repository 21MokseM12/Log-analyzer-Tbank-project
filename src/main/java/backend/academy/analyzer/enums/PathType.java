package backend.academy.analyzer.enums;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public enum PathType {
    URL, LOCAL;

    public static PathType identify(String path) {
        try {
            URI uri = new URI(path);
            if (uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https"))) {
                return PathType.URL;
            }
            throw new URISyntaxException(uri.toString(), "Path is not URI");
        } catch (URISyntaxException e) {
            try {
                Path localPath = Paths.get(path);
                if (Files.exists(localPath)) {
                    return PathType.LOCAL;
                } else {
                    throw new InvalidPathException("Указанный путь не путь к конкретному файлу", "");
                }
            } catch (InvalidPathException invalidPathException) {
                try {
                    PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + path);
                    return PathType.LOCAL;
                } catch (IllegalArgumentException exception) {
                    throw new NoSuchElementException();
                }
            }
        }
    }
}
