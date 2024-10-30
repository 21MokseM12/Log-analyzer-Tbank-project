package backend.academy.analyzer.validators;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class PathParameterValidator implements ParameterValidator {

    @Override
    public boolean isValid(String paramBody) {
        if (paramBody.isEmpty()) {
            return false;
        }
        if (isValidUrl(paramBody)) {
            return true;
        } else {
            return isValidFilePath(paramBody);
        }
    }

    private boolean isValidUrl(String url) {
        try {
            URI uri = new URI(url);
            return uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https"));
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private boolean isValidFilePath(String path) {
        if (isValidLocalPath(path)) {
            return true;
        } else {
            return isValidPathPattern(path);
        }
    }

    private boolean isValidLocalPath(String input) {
        try {
            Path path = Paths.get(input);
            return Files.exists(path) && Files.isRegularFile(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    private boolean isValidPathPattern(String input) {
        String regex = ".*[?*].*";
        boolean first = Pattern.matches(regex, input);
        try {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + input);
            return first;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
