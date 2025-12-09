# Sistema de Gestión de Eventos Musicales - JPA

Proyecto del trabajo tutelado de Ingeniería de Datos que implementa un sistema de gestión de eventos musicales (conciertos, festivales y artistas) utilizando JPA/Hibernate.

## 📋 Descripción

Sistema que modela eventos musicales con las siguientes entidades:
- **EventoMusical** (superclase abstracta)
- **Concierto** - eventos individuales con aforo
- **Festival** - eventos de múltiples días con diferentes estilos
- **Artista** - intérpretes que participan en los eventos

## 🛠️ Tecnologías

- Java 11+
- Maven 3.9+
- JPA 2.0 / Hibernate 5.4.21
- PostgreSQL 42.5.0
- JUnit 4.12
- Log4j 2.12.1

## 📦 Instalación

### Prerrequisitos

1. Java JDK 11 o superior
2. Maven 3.6 o superior
3. PostgreSQL

### Configuración de Base de Datos

Crea una base de datos PostgreSQL:
```sql
CREATE DATABASE tuteladoDB;
CREATE USER myuser WITH PASSWORD 'myuser123';
GRANT ALL PRIVILEGES ON DATABASE tuteladoDB TO myuser;
```

### Compilación

```bash
mvn clean compile
```

## 🚀 Ejecución

### Ejecutar la aplicación principal:
```bash
mvn exec:java -Dexec.mainClass="gei.id.tutelado.App"
```

### Ejecutar tests:
```bash
mvn test
```

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/gei/id/tutelado/
│   │   ├── model/              # Entidades JPA
│   │   │   ├── EventoMusical.java
│   │   │   ├── Concierto.java
│   │   │   ├── Festival.java
│   │   │   └── Artista.java
│   │   ├── dao/                # Capa de persistencia
│   │   │   ├── ConciertoDaoJPA.java
│   │   │   └── ArtistaDaoJPA.java
│   │   ├── configuracion/      # Configuración JPA
│   │   └── App.java
│   └── resources/
│       └── META-INF/
│           └── persistence.xml
└── test/
    └── java/gei/id/tutelado/
        ├── P01_EventosMusicales.java
        └── ProductorDatosProba.java
```

## ✨ Características Implementadas

### Modelo de Datos
- ✅ Herencia con estrategia JOINED
- ✅ Relaciones bidireccionales
- ✅ Estrategias EAGER y LAZY
- ✅ Propagación CASCADE
- ✅ Identificadores con @TableGenerator

### Consultas JPQL
- ✅ INNER JOIN - Contar conciertos por artista
- ✅ OUTER JOIN - Buscar todos los conciertos
- ✅ Subconsulta - Artistas con más conciertos que la media
- ✅ Agregación - Contar artistas por género

### Tests
- ✅ Test de recuperación por clave natural
- ✅ Test de operaciones CRUD
- ✅ Test de inicialización LAZY
- ✅ Test de carga EAGER
- ✅ Test de propagación CASCADE
- ✅ Test de consultas JPQL

## 👥 Colaboración

Para trabajar en el proyecto:

1. Clona el repositorio:
```bash
git clone <URL_DEL_REPOSITORIO>
```

2. Crea una rama para tus cambios:
```bash
git checkout -b feature/nueva-funcionalidad
```

3. Realiza tus cambios y haz commit:
```bash
git add .
git commit -m "Descripción de los cambios"
```

4. Sube tus cambios:
```bash
git push origin feature/nueva-funcionalidad
```

## 📝 Configuración

La configuración de la conexión a la base de datos se encuentra en:
`src/main/resources/META-INF/persistence.xml`

Modifica estos valores según tu configuración:
```xml
<property name="hibernate.connection.url" value="jdbc:postgresql://localhost:5432/tuteladoDB" />
<property name="hibernate.connection.username" value="myuser" />
<property name="hibernate.connection.password" value="myuser123" />
```

## 📄 Licencia

Proyecto académico - Universidad de A Coruña

## 👤 Autores

- María y compañero

---

Para más información, consulta el archivo `RESUMEN_IMPLEMENTACION.md`
