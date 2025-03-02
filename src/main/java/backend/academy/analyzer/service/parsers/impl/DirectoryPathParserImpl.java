package backend.academy.analyzer.service.parsers.impl;

import backend.academy.analyzer.service.parsers.interfaces.DirectoryPathParser;
import java.nio.file.Path;
import lombok.Getter;

public class DirectoryPathParserImpl implements DirectoryPathParser {

    private static final String META_SYMBOLS = "*?[]{}";

    private final String[] splitPath;

    @Getter private int beginWildcardIndex;

    public DirectoryPathParserImpl(String stringPath) {
        beginWildcardIndex = -1;

        this.splitPath = stringPath.split("/");
        for (int g = 0; g < splitPath.length; g++) {
            for (int i = 0; i < splitPath[g].length(); i++) {
                if (META_SYMBOLS.indexOf(splitPath[g].charAt(i)) != -1) {
                    beginWildcardIndex = g;
                    return;
                }
            }
        }
    }

    public Path getParentDirectory() {
        return Path.of(buildPath(splitPath, 0, beginWildcardIndex));
    }

    public String getFileNamePattern() {
        return buildPath(splitPath, splitPath.length - 1, splitPath.length);
    }

    private String buildPath(String[] splitPath, int startIndex, int stopIndex) {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < splitPath.length; i++) {
            if (i == stopIndex) {
                break;
            } else {
                builder.append(splitPath[i]).append("/");
            }
        }

        return builder.delete(builder.length() - 1, builder.length()).toString();
    }
}
