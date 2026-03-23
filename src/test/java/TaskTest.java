import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void markDone_setsTaskToDone() {
        Task task = new Task(1, "estudiar", null);

        task.markDone();

        assertTrue(task.isDone());
    }

    @Test
    void constructor_rejectsZeroId() {
        assertThrows(IllegalArgumentException.class, () -> new Task(0, "ok", null));
    }

    @Test
    void constructor_rejectsNegativeId() {
        assertThrows(IllegalArgumentException.class, () -> new Task(-1, "ok", null));
    }

    @Test
    void constructor_rejectsNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1, null, null));
    }

    @Test
    void constructor_rejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Task(1, "   ", null));
    }

    @Test
    void constructor_setsEmptyDescriptionWhenNull() {
        Task task = new Task(1, "estudiar", null);

        assertEquals("", task.getDescription());
    }

    @Test
    void constructor_keepsProvidedDescription() {
        Task task = new Task(1, "estudiar", "ir al supermercado");

        assertEquals("ir al supermercado", task.getDescription());
    }

    @Test
    void constructor_trimsTitle() {
        Task task = new Task(1, "  estudiar  ", null);

        assertEquals("estudiar", task.getTitle());
    }

    @Test
    void equalsAndHashCode_areBasedOnlyOnId() {
        Task task1 = new Task(1, "estudiar", "desc 1");
        Task task2 = new Task(1, "trabajar", "desc 2");

        assertEquals(task1, task2);
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    void equals_returnsFalseWhenIdsAreDifferent() {
        Task task1 = new Task(1, "estudiar", "desc");
        Task task2 = new Task(2, "estudiar", "desc");

        assertNotEquals(task1, task2);
    }
}