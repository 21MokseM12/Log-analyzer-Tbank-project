package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.enums.PathType;
import backend.academy.analyzer.service.parsers.interfaces.ResourceParser;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        List<String> resourceNames = new ArrayList<>();
        try {
            DirectoryPathParserImpl directoryParser = new DirectoryPathParserImpl(stringPath);
            if (directoryParser.beginWildcardIndex() != -1) {
                Path parent = directoryParser.getParentDirectory();
                try (DirectoryStream<Path> directoryStream =
                         Files.newDirectoryStream(parent, directoryParser.getPathPattern())) {
                    for (Path entry : directoryStream) {
                        resourceNames.add(entry.getFileName().toString());
                    }
                }
            } else {
                resourceNames.add(stringPath.substring(stringPath.lastIndexOf("/") + 1));
            }

            return resourceNames;
        } catch (IOException e) {
            log.error("Указанный локальный путь не был найден", e);
            throw new NoSuchElementException();
        }
    }

    private List<String> parseUrlResourceName(String stringPath) {
        List<String> pathNames = new ArrayList<>();
        String[] splitPath = stringPath.split("/");
        pathNames.add(splitPath[splitPath.length-1]);
        return pathNames;
    }
}
