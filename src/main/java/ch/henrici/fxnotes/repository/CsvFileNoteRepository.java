package ch.henrici.fxnotes.repository;

import ch.henrici.fxnotes.model.Note;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class CsvFileNoteRepository extends AbstractNoteRepository {

    private final Path filePath;

    public CsvFileNoteRepository(Path filePath, SaveStrategy saveStrategy) {
        super(saveStrategy);
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile() {
        cache.clear();

        try {
            if (!Files.exists(filePath)) {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header && line.startsWith("id;")) {
                    header = false;
                    continue;
                }
                header = false;
                if (line.isBlank()) continue;

                String[] parts = line.split(";", -1);
                if (parts.length < 3) continue;

                String id = parts[0];
                String title = unescape(parts[1]);
                String content = unescape(parts[2]);

                cache.add(new Note(id, title, content));

                try {
                    int n = Integer.parseInt(id);
                    idCounter = Math.max(idCounter, n + 1);
                } catch (NumberFormatException ignored) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\n", "\\n");
    }

    private String unescape(String s) {
        if (s == null) return "";
        return s.replace("\\n", "\n");
    }

    @Override
    protected void writeToStorage() {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write("id;title;content");
            writer.newLine();

            for (Note note : cache) {
                writer.write(note.getId() + ";" +
                        escape(note.getTitle()) + ";" +
                        escape(note.getContent()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}