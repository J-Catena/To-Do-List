import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @Test
    void addTask_rejectsDuplicateId() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task(1, "a", null));

        assertThrows(IllegalArgumentException.class,
                () -> manager.addTask(new Task(1, "b", null)));
    }

    @Test
    void removeTask_whenIdNotFound() {
        TaskManager manager = new TaskManager();

        assertThrows(IllegalArgumentException.class, () -> manager.removeTask(999));
    }

    @Test
    void getTasks_returnsTasksSortedById() {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(3, "c", null));
        manager.addTask(new Task(1, "a", null));
        manager.addTask(new Task(2, "b", null));

        List<Task> tasks = manager.getTasks();

        assertEquals(3, tasks.size());

        assertEquals(1, tasks.get(0).getId());
        assertEquals(2, tasks.get(1).getId());
        assertEquals(3, tasks.get(2).getId());
    }

    @Test
    void markTaskAsDone_setsTaskDoneToTrue() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task(1, "a", null));

        manager.markTaskAsDone(1);

        Task task = manager.findTaskById(1);
        assertTrue(task.isDone());
    }

    @Test
    void findTaskById_throwsWhenTaskDoesNotExist() {
        TaskManager manager = new TaskManager();

        assertThrows(IllegalArgumentException.class, () -> manager.findTaskById(999));
    }

    @Test
    void findTaskById_returnsTaskWhenItExists() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task(1, "a", null));

        Task task = manager.findTaskById(1);

        assertEquals(1, task.getId());
    }

    @Test
    void count_returnsZeroWhenManagerIsEmpty() {
        TaskManager manager = new TaskManager();

        assertEquals(0, manager.count());
    }

    @Test
    void count_increasesAfterAddTask() {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(1, "a", null));

        assertEquals(1, manager.count());
    }

    @Test
    void count_decreasesAfterRemoveTask() {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(1, "a", null));
        manager.removeTask(1);

        assertEquals(0, manager.count());
    }

    @Test
    void removeTask_removesExistingTask() {
        TaskManager manager = new TaskManager();
        manager.addTask(new Task(1, "a", null));

        manager.removeTask(1);

        assertThrows(IllegalArgumentException.class, () -> manager.findTaskById(1));
    }

    @Test
    void markTaskAsDone_throwsWhenTaskDoesNotExist() {
        TaskManager manager = new TaskManager();

        assertThrows(IllegalArgumentException.class, () -> manager.markTaskAsDone(999));
    }
}