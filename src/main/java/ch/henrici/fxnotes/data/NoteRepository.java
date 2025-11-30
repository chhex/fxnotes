package ch.henrici.fxnotes.data;

import ch.henrici.fxnotes.model.Note;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private final boolean useMock;
    private final Path filePath;

    private final List<Note> cache = new ArrayList<>();
    private int idCounter = 1;

    public NoteRepository(boolean useMock, Path filePath) {
        this.useMock = useMock;
        this.filePath = filePath;

        if (useMock) {
            loadMockData();
        } else {
            loadFromFile();
        }
    }

    private void loadMockData() {
        cache.clear();
        cache.add(new Note(nextId(), "Hali Halo", "This is a mock note."));
        cache.add(new Note(nextId(), "Hola", "Esta una mock repository running."));
    }

    private String nextId() {
        return String.valueOf(idCounter++);
    }

    public List<Note> findAll() {
        return new ArrayList<>(cache);
    }

    public Note save(Note note) {
        // NEW?
        if (note.getId() == null || note.getId().isBlank()) {
            note = new Note(nextId(), note.getTitle(), note.getContent());
        } else {
            deleteById(note.getId());
        }

        cache.add(note);

        if (!useMock) {
            writeToFile();
        }
        return note;
    }

    public void deleteById(String id) {
        cache.removeIf(n -> n.getId().equals(id));

        if (!useMock) {
            writeToFile();
        }
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

        try (BufferedReader reader =
                     Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;
            boolean header = true;
            while ((line = reader.readLine()) != null) {
                if (header && line.startsWith("id;")) {
                    header = false;
                    continue;
                }
                header = false;

                if (line.isBlank()) continue;

                String[] p = line.split(";", -1);
                if (p.length < 3) continue;

                String id = p[0];
                String title = unescape(p[1]);
                String content = unescape(p[2]);

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

    private void writeToFile() {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {

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

    private String escape(String s) {
        return s.replace("\n", "\\n");
    }

    private String unescape(String s) {
        return s.replace("\\n", "\n");
    }
}