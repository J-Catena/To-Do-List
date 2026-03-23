import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String TASKS_FILE = "tasks.tsv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        TaskFileRepository repository = new TaskFileRepository();

        try {
            taskManager.addAll(repository.load(TASKS_FILE));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("No se pudieron cargar las tareas: " + e.getMessage());
        }

        boolean running = true;

        while (running) {
            printMenu();
            String option = sc.nextLine().trim();

            switch (option) {
                case "0" -> running = false;
                case "1" -> handleListTasks(taskManager);
                case "2" -> handleCreateTask(sc, taskManager);
                case "3" -> handleMarkTaskAsDone(sc, taskManager);
                case "4" -> handleRemoveTask(sc, taskManager);
                default -> System.out.println("Opción inválida.");
            }
        }

        try {
            repository.save(TASKS_FILE, taskManager.getTasks());
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("No se pudieron guardar las tareas: " + e.getMessage());
        }

        System.out.println("Saliendo...");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n=== TODO MVP ===");
        System.out.println("0) Salir");
        System.out.println("1) Listar tareas");
        System.out.println("2) Crear tarea");
        System.out.println("3) Marcar tarea como hecha");
        System.out.println("4) Eliminar tarea");
        System.out.print("Opción: ");
    }

    private static void handleListTasks(TaskManager taskManager) {
        List<Task> tasks = taskManager.getTasks();

        if (tasks.isEmpty()) {
            System.out.println("No hay tareas.");
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }

        System.out.println("Total: " + taskManager.count());
    }

    private static void handleCreateTask(Scanner sc, TaskManager taskManager) {
        try {
            int id = readInt(sc, "Ingrese id: ");

            System.out.print("Ingrese título: ");
            String title = sc.nextLine().trim();

            System.out.print("Ingrese descripción (opcional): ");
            String description = sc.nextLine().trim();

            Task task = new Task(id, title, description.isBlank() ? null : description);
            taskManager.addTask(task);

            System.out.println("Tarea creada.");
        } catch (NumberFormatException e) {
            System.out.println("Id inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleMarkTaskAsDone(Scanner sc, TaskManager taskManager) {
        try {
            int id = readInt(sc, "Ingrese id: ");
            taskManager.markTaskAsDone(id);
            System.out.println("Tarea completada.");
        } catch (NumberFormatException e) {
            System.out.println("Id inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void handleRemoveTask(Scanner sc, TaskManager taskManager) {
        try {
            int id = readInt(sc, "Ingrese id: ");
            taskManager.removeTask(id);
            System.out.println("Tarea eliminada.");
        } catch (NumberFormatException e) {
            System.out.println("Id inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static int readInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(sc.nextLine().trim());
    }
}