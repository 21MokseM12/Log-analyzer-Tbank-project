package backend.academy.analyzer.validators;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathParameterValidator implements ParameterValidator {

    @Override
    public boolean isValid(String paramBody) {
        try {
            URI uri = new URI(paramBody);
            if (uri.getScheme() != null && (uri.getScheme().equals("http") || uri.getScheme().equals("https"))) {
                return true;
            }
            throw new URISyntaxException(uri.toString(), "Path is not URI");
        } catch (URISyntaxException _) {
            Path path = Paths.get(paramBody);
            return Files.exists(path);
        }
    }
}
