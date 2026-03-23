public class Task {
    private final int id;
    private String title;
    private String description;
    private boolean done;

    public Task(int id, String title, String description) {
        if (id <= 0) {
            throw new IllegalArgumentException("id debe ser superior a 0");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("debe asignar un titulo a la tarea");
        }

        this.id = id;
        this.title = title.trim();
        this.description = (description == null) ? "" : description.trim();
        this.done = false;
    }

    public void markDone() {
        this.done = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        String status;
        if (done) {
            status = "[x]";
        } else {
            status = "[ ]";
        }

        String base = status + " " + id + " - " + title;

        if (!description.isEmpty()) {
            return base + " (" + description + ")";
        } else {
            return base;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }


}


