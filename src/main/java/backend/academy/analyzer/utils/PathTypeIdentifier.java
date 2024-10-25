package backend.academy.analyzer.utils;

import backend.academy.analyzer.enums.PathType;
import lombok.experimental.UtilityClass;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

@UtilityClass
public class PathTypeIdentifier {

    public static PathType identify(String path) {
        try {
            URI uri = new URI(path);
            if (uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https"))) {
                return PathType.URL;
            }
            throw new URISyntaxException(uri.toString(), "Path is not URI");
        } catch (URISyntaxException _) {
            Path localPath = Paths.get(path);
            if (Files.exists(localPath)) {
                return PathType.LOCAL;
            } else {
                throw new NoSuchElementException("Указанный путь не найден");
            }
        }
    }
}
