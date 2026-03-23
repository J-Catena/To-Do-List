
import java.util.*;

public class TaskManager {

    private static final String TASK_NOT_FOUND = "Tarea no encontrada: ";

    private final Map<Integer, Task> tasks;

    public TaskManager() {
        tasks = new HashMap<>();
    }

    public void addTask(Task task) {
        Task existing = tasks.putIfAbsent(task.getId(), task);
        if (existing != null) {
            throw new IllegalArgumentException(
                    "Task id already exists: " + task.getId() +
                            " (existing title='" + existing.getTitle() +
                            "', new title='" + task.getTitle() + "')"
            );
        }
    }

    public Task findTaskById(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new IllegalArgumentException(TASK_NOT_FOUND + id);
        }
        return task;
    }


    public void markTaskAsDone(int id) {
        Task task = findTaskById(id);
        task.markDone();
    }

    public void removeTask(int id) {
        findTaskById(id);
        tasks.remove(id);
    }

    public int count() {
        return tasks.size();
    }

    public List<Task> getTasks() {
        List<Task> taskList = new ArrayList<>(tasks.values());
        taskList.sort(Comparator.comparingInt(Task::getId));
        return taskList;
    }

    public void addAll(List<Task> tasks) {
        for (Task task : tasks) {
            addTask(task);
        }
    }
}