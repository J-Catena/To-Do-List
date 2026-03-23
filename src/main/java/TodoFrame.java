import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

public class TodoFrame extends JFrame {

    private static final String TASKS_FILE = "tasks.tsv";

    private final TaskManager taskManager;
    private final TaskFileRepository repository;

    private JTextArea tasksArea;
    private JTextField idField;
    private JTextField titleField;
    private JTextField descriptionField;
    private JLabel totalLabel;

    public TodoFrame() {
        this.taskManager = new TaskManager();
        this.repository = new TaskFileRepository();

        loadTasks();
        initUi();
        refreshTasks();
    }

    private void initUi() {
        setTitle("ToDoList Java");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBorder(new EmptyBorder(20, 20, 20, 20));
        root.setBackground(new Color(245, 247, 250));
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildCenterPanel(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveTasksAndExit();
            }
        });
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel titleLabel = new JLabel("ToDoList");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));

        JLabel subtitleLabel = new JLabel("Java 21 · Swing · Persistencia TSV");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(90, 90, 90));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subtitleLabel);

        header.add(textPanel, BorderLayout.WEST);

        totalLabel = new JLabel("Total: 0");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        header.add(totalLabel, BorderLayout.EAST);

        return header;
    }

    private JPanel buildCenterPanel() {
        JPanel center = new JPanel(new GridLayout(1, 2, 16, 16));
        center.setOpaque(false);

        center.add(buildTaskListPanel());
        center.add(buildFormPanel());

        return center;
    }

    private JPanel buildTaskListPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230)),
                new EmptyBorder(16, 16, 16, 16)
        ));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Tareas");
        label.setFont(new Font("SansSerif", Font.BOLD, 18));

        tasksArea = new JTextArea();
        tasksArea.setEditable(false);
        tasksArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        tasksArea.setLineWrap(true);
        tasksArea.setWrapStyleWord(true);
        tasksArea.setBackground(new Color(250, 250, 250));
        tasksArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(tasksArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230)),
                new EmptyBorder(16, 16, 16, 16)
        ));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Gestión");
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        idField = new JTextField();
        titleField = new JTextField();
        descriptionField = new JTextField();

        styleTextField(idField);
        styleTextField(titleField);
        styleTextField(descriptionField);

        addField(fieldsPanel, gbc, 0, "ID", idField);
        addField(fieldsPanel, gbc, 1, "Título", titleField);
        addField(fieldsPanel, gbc, 2, "Descripción", descriptionField);

        panel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonsPanel.setOpaque(false);

        JButton createButton = new JButton("Crear");
        JButton doneButton = new JButton("Marcar hecha");
        JButton removeButton = new JButton("Eliminar");
        JButton refreshButton = new JButton("Refrescar");

        styleButton(createButton);
        styleButton(doneButton);
        styleButton(removeButton);
        styleButton(refreshButton);

        createButton.addActionListener(e -> createTask());
        doneButton.addActionListener(e -> markTaskAsDone());
        removeButton.addActionListener(e -> removeTask());
        refreshButton.addActionListener(e -> refreshTasks());

        buttonsPanel.add(createButton);
        buttonsPanel.add(doneButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(refreshButton);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setOpaque(false);

        JLabel info = new JLabel("Los cambios se guardan automáticamente en tasks.tsv");
        info.setFont(new Font("SansSerif", Font.PLAIN, 13));
        info.setForeground(new Color(100, 100, 100));

        footer.add(info);

        return footer;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(labelText + ":"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 34));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);
    }

    private void loadTasks() {
        try {
            taskManager.addAll(repository.load(TASKS_FILE));
        } catch (IOException | IllegalArgumentException e) {
            showError("No se pudieron cargar las tareas: " + e.getMessage());
        }
    }

    private void saveTasks() {
        try {
            repository.save(TASKS_FILE, taskManager.getTasks());
        } catch (IOException | IllegalArgumentException e) {
            showError("No se pudieron guardar las tareas: " + e.getMessage());
        }
    }

    private void saveTasksAndExit() {
        saveTasks();
        dispose();
        System.exit(0);
    }

    private void createTask() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String title = titleField.getText().trim();
            String description = descriptionField.getText().trim();

            Task task = new Task(id, title, description.isBlank() ? null : description);
            taskManager.addTask(task);

            saveTasks();
            clearInputs();
            refreshTasks();
            showInfo("Tarea creada.");
        } catch (NumberFormatException e) {
            showError("Id inválido.");
            clearInputs();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            clearInputs();
        }
    }

    private void markTaskAsDone() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            taskManager.markTaskAsDone(id);

            saveTasks();
            clearInputs();
            refreshTasks();
            showInfo("Tarea completada.");
        } catch (NumberFormatException e) {
            showError("Id inválido.");
            clearInputs();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            clearInputs();
        }
    }

    private void removeTask() {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Eliminar tarea con id " + id + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            taskManager.removeTask(id);

            saveTasks();
            clearInputs();
            refreshTasks();
            showInfo("Tarea eliminada.");
        } catch (NumberFormatException e) {
            showError("Id inválido.");
            clearInputs();
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            clearInputs();
        }
    }

    private void refreshTasks() {
        List<Task> tasks = taskManager.getTasks();

        if (tasks.isEmpty()) {
            tasksArea.setText("No hay tareas.");
            totalLabel.setText("Total: 0");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task).append(System.lineSeparator());
        }

        tasksArea.setText(sb.toString());
        totalLabel.setText("Total: " + taskManager.count());
    }

    private void clearInputs() {
        idField.setText("");
        titleField.setText("");
        descriptionField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}