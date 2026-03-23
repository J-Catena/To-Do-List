import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void load_returnsEmptyListWhenFileDoesNotExist() throws IOException {
        TaskFileRepository repository = new TaskFileRepository();
        Path file = tempDir.resolve("tasks.tsv");

        List<Task> tasks = repository.load(file.toString());

        assertTrue(tasks.isEmpty());
    }

    @Test
    void save_andLoad_preservesTasks() throws IOException {
        TaskFileRepository repository = new TaskFileRepository();
        Path file = tempDir.resolve("tasks.tsv");

        Task task1 = new Task(1, "estudiar", "java");
        Task task2 = new Task(2, "trabajar", "");
        task2.markDone();

        List<Task> original = List.of(task1, task2);

        repository.save(file.toString(), original);
        List<Task> loaded = repository.load(file.toString());

        assertEquals(2, loaded.size());

        assertEquals(1, loaded.get(0).getId());
        assertEquals("estudiar", loaded.get(0).getTitle());
        assertEquals("java", loaded.get(0).getDescription());
        assertFalse(loaded.get(0).isDone());

        assertEquals(2, loaded.get(1).getId());
        assertEquals("trabajar", loaded.get(1).getTitle());
        assertEquals("", loaded.get(1).getDescription());
        assertTrue(loaded.get(1).isDone());
    }

    @Test
    void load_throwsWhenBooleanIsInvalid() throws IOException {
        TaskFileRepository repository = new TaskFileRepository();
        Path file = tempDir.resolve("tasks.tsv");

        Files.writeString(file, "1\tmaybe\testudiar\tdesc");

        assertThrows(IllegalArgumentException.class, () -> repository.load(file.toString()));
    }

    @Test
    void load_throwsWhenLineHasMissingColumns() throws IOException {
        TaskFileRepository repository = new TaskFileRepository();
        Path file = tempDir.resolve("tasks.tsv");

        Files.writeString(file, "1\ttrue\testudiar");

        assertThrows(IllegalArgumentException.class, () -> repository.load(file.toString()));
    }

    @Test
    void load_throwsWhenIdIsInvalid() throws IOException {
        TaskFileRepository repository = new TaskFileRepository();
        Path file = tempDir.resolve("tasks.tsv");

        Files.writeString(file, "abc\ttrue\testudiar\tdesc");

        assertThrows(IllegalArgumentException.class, () -> repository.load(file.toString()));
    }
}