package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.enums.PathType;
import backend.academy.analyzer.service.parsers.interfaces.ResourceParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResourceParserImpl implements ResourceParser {

    @Override
    public List<String> parsePathResourceName(PathType type, String stringPath) {
        return switch (type) {
            case PathType.LOCAL -> parseLocalFileResourceNames(stringPath);
            case PathType.URL -> parseUrlResourceName(stringPath);
        };
    }

    private List<String> parseLocalFileResourceNames(String stringPath) {
        try {
            DirectoryPathParserImpl directoryParser = new DirectoryPathParserImpl(stringPath);
            if (directoryParser.beginWildcardIndex() != -1) {
                Path parent = directoryParser.getParentDirectory();
                String pattern = getRegexPattern(directoryParser.getFileNamePattern());

                return Files.walk(parent)
                    .filter(path -> !Files.isDirectory(path))
                    .filter(path -> path.getFileName().toString().matches(pattern))
                    .map(path -> path.getFileName().toString())
                    .toList();
            } else {
                return List.of(stringPath.substring(stringPath.lastIndexOf("/") + 1));
            }
        } catch (IOException e) {
            log.error("Указанный локальный путь не был найден", e);
            throw new NoSuchElementException();
        }
    }

    private List<String> parseUrlResourceName(String stringPath) {
        List<String> pathNames = new ArrayList<>();
        String[] splitPath = stringPath.split("/");
        pathNames.add(splitPath[splitPath.length - 1]);
        return pathNames;
    }

    private String getRegexPattern(String path) {
        path = path.replace("**", "");
        path = path.replace("*", "[^/]*");
        path = path.replace("?", ".");
        return path;
    }
}
