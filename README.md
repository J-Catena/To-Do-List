# ToDoList — Java 21 Console & Swing Application

Aplicación de gestión de tareas desarrollada en **Java puro (Java 21)** sin frameworks, con arquitectura orientada a backend real.

El objetivo del proyecto es demostrar:

- diseño de dominio
- separación de responsabilidades
- testing con JUnit 5
- persistencia simple en fichero
- evolución progresiva de arquitectura
- interfaz gráfica básica con Swing

---

## Funcionalidades

- Crear tareas
- Listar tareas ordenadas por id
- Marcar tareas como completadas
- Eliminar tareas
- Contador total de tareas
- Persistencia automática en fichero TSV
- Interfaz gráfica básica (Swing)
- Tests unitarios del dominio y lógica de negocio

---

## Arquitectura

El proyecto está estructurado en capas simples simulando un backend real:

### Dominio
**Task**

- id inmutable
- validación de id > 0
- título obligatorio y normalizado (trim)
- descripción nunca null
- estado `done`
- igualdad basada solo en id

### Lógica de negocio
**TaskManager**

Responsable de:

- añadir tareas
- evitar ids duplicados
- buscar tareas
- marcar tareas como completadas
- eliminar tareas
- devolver lista ordenada
- contar tareas

### Persistencia
**TaskFileRepository**

- almacenamiento en fichero TSV
- carga al iniciar la aplicación
- guardado automático tras operaciones

Formato del fichero:


id \t done \t title \t description


---

## Interfaz gráfica

El proyecto incluye una interfaz gráfica desarrollada con **Swing** que permite:

- visualizar tareas
- crear nuevas tareas
- marcar tareas como completadas
- eliminar tareas con confirmación
- persistencia automática
- contador total visible

La GUI actúa únicamente como capa de presentación, delegando toda la lógica en `TaskManager`.

---

## Testing

Testing realizado con **JUnit 5**.

Cobertura principal:

- validaciones del dominio
- comportamiento de `TaskManager`
- duplicados
- ordenación
- conteo
- excepciones esperadas

---

## Tecnologías utilizadas

- Java 21
- Maven
- JUnit 5
- Swing
- Persistencia en fichero (TSV)
- IntelliJ IDEA

---

##  Cómo ejecutar

### 1. Clonar el repositorio


git clone https://github.com/J-Catena/To-Do-List.git


### 2. Ejecutar desde IntelliJ o Maven

Clase principal:


Main


La aplicación abrirá la interfaz gráfica.

---

## Persistencia

Las tareas se almacenan automáticamente en:


tasks.tsv


Si el fichero no existe, se crea automáticamente.

---

## Objetivo del proyecto

Este proyecto forma parte de un proceso de aprendizaje orientado a:

- desarrollo backend en Java
- diseño de aplicaciones sin frameworks
- preparación para evolucionar posteriormente a APIs REST
- construcción de piezas de portfolio profesionales

---

## Posibles mejoras futuras

- API REST con Spring Boot
- persistencia en base de datos
- interfaz JavaFX o web
- filtrado y búsqueda de tareas
- selección de tareas desde la lista
- mejora visual avanzada

---

## Autor

Juan Catena  
Backend Java Developer