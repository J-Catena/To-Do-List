import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskFileRepository {

    private static final String SEPARATOR = "\t";
    private static final int EXPECTED_COLUMNS = 4;

    public void save(String path, List<Task> tasks) throws IOException {
        Path file = Path.of(path);

        try (BufferedWriter writer = Files.newBufferedWriter(file)) {
            for (Task task : tasks) {
                validateTaskForTsv(task);

                writer.write(
                        task.getId() + SEPARATOR +
                                Boolean.toString(task.isDone()) + SEPARATOR +
                                task.getTitle() + SEPARATOR +
                                task.getDescription()
                );
                writer.newLine();
            }
        }
    }

    public List<Task> load(String path) throws IOException {
        Path file = Path.of(path);
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(file)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(file);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.isBlank()) {
                continue;
            }

            tasks.add(parseLine(line, i + 1));
        }

        return tasks;
    }

    private Task parseLine(String line, int lineNumber) {
        String[] parts = line.split(SEPARATOR, -1);

        if (parts.length != EXPECTED_COLUMNS) {
            throw new IllegalArgumentException(
                    "Invalid TSV format at line " + lineNumber +
                            ": expected " + EXPECTED_COLUMNS +
                            " columns but got " + parts.length
            );
        }

        int id;
        try {
            id = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid id at line " + lineNumber + ": " + parts[0], e);
        }

        boolean done = parseDone(parts[1], lineNumber);
        String title = parts[2];
        String description = parts[3];

        Task task;
        try {
            task = new Task(id, title, description);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid task data at line " + lineNumber + ": " + e.getMessage(), e);
        }

        if (done) {
            task.markDone();
        }

        return task;
    }

    private boolean parseDone(String value, int lineNumber) {
        if ("true".equals(value)) {
            return true;
        }
        if ("false".equals(value)) {
            return false;
        }

        throw new IllegalArgumentException("Invalid done value at line " + lineNumber + ": " + value);
    }

    private void validateTaskForTsv(Task task) {
        if (task.getTitle().contains(SEPARATOR)) {
            throw new IllegalArgumentException("Title cannot contain tab characters");
        }
        if (task.getDescription().contains(SEPARATOR)) {
            throw new IllegalArgumentException("Description cannot contain tab characters");
        }
    }
}