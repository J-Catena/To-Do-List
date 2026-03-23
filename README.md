# ToDoList Console App (Java 21)

Aplicación de gestión de tareas desarrollada en **Java puro (sin frameworks)** con persistencia en fichero y tests automatizados.

El objetivo del proyecto es practicar fundamentos reales de backend:

- diseño de dominio
- separación de responsabilidades
- testing con JUnit
- persistencia simple
- manejo de errores
- estructura Maven

---

## Stack tecnológico

- Java 21
- Maven
- JUnit 5
- IntelliJ IDEA

---

## Funcionalidades

- Crear tarea
- Listar tareas
- Marcar tarea como hecha
- Eliminar tarea
- Guardado automático en fichero
- Carga automática al iniciar
- Validaciones de dominio
- Tests unitarios

---

## Modelo de dominio

### Task
Entidad que representa una tarea.

Reglas:

- id obligatorio y positivo
- title obligatorio y no vacío
- description nunca es null
- done indica si la tarea está completada
- igualdad basada solo en id

---

### TaskManager
Responsable de la lógica de negocio:

- evita ids duplicados
- permite marcar tareas como hechas
- permite eliminar tareas
- devuelve tareas ordenadas por id
- mantiene el conteo de tareas

---

### Persistencia

Se usa `TaskFileRepository` con formato **TSV**.

Formato:


id done title description


Ejemplo:


1 false estudiar java
2 true trabajar


El repositorio:

- valida el formato al cargar
- lanza error si los datos son inválidos
- evita corrupción silenciosa

---

## Estructura del proyecto


src
├─ main/java
│ ├─ Task.java
│ ├─ TaskManager.java
│ ├─ TaskFileRepository.java
│ └─ Main.java
│
└─ test/java
├─ TaskTest.java
├─ TaskManagerTest.java
└─ TaskFileRepositoryTest.java


---

## Ejecutar la aplicación

Desde terminal:

```bash
mvn compile
mvn exec:java

También puede ejecutarse directamente desde IntelliJ ejecutando la clase Main