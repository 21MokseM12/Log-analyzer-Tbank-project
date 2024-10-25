package backend.academy.analyzer.service.readers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogUrlReader implements LogReader {

    @Override
    public Stream<String> read(String stringPath) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest
                .newBuilder()
                .uri(new URI(stringPath))
                .GET()
                .build();

            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            return response.body().lines();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error("URL не был найден", e);
            throw new NoSuchElementException();
        }
    }
}
