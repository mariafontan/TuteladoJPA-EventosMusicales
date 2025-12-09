# RESUMEN DE LA IMPLEMENTACIÓN - TRABAJO TUTELADO JPA

## Dominio: Sistema de Gestión de Eventos Musicales

### 1. MODELO DE NEGOCIO (Capa de Negocio)

#### Clases Implementadas:

1. **EventoMusical** (superclase abstracta)
   - Atributos: id_evento (Long), codigo_evento (Integer - clave natural), nombre, fecha, lugar
   - Estrategia de herencia: **JOINED** (InheritanceType.JOINED)
   - Identificador con @TableGenerator para asignación automática
   - Métodos equals() y hashCode() usando clave natural (codigo_evento)
   - Método funcionhash() implementado

2. **Concierto** (hereda de EventoMusical)
   - Atributo específico: aforo (Integer)
   - Relación bidireccional con Artista (**ManyToMany, LAZY, CASCADE**)
   - Métodos de conveniencia para gestionar artistas

3. **Festival** (hereda de EventoMusical)
   - Atributo específico: num_dias (Integer)
   - Colección de estilos musicales (@ElementCollection, LAZY)
   - Relación con Artista (ManyToMany)

4. **Artista**
   - Atributos: id_artista, codigo_artista (clave natural), nombre, genero
   - Relación bidireccional con Concierto (**EAGER** - carga automática)
   - Relación con Festival (LAZY)
   - Identificador con @TableGenerator
   - Métodos equals(), hashCode() y funcionhash()

### 2. MAPEO JPA

✅ **Estrategias implementadas:**
- **EAGER**: Conciertos en Artista (carga automática)
- **LAZY**: Artistas en Concierto, Festivales en Artista, Estilos en Festival
- **CASCADE**: PERSIST y MERGE en relación Concierto-Artista
- **Herencia**: Estrategia JOINED para EventoMusical

### 3. CAPA DE PERSISTENCIA (DAOs)

#### ConciertoDaoJPA
- **MO4.1**: recuperaPorCodigo() - consulta estática JPQL
- **MO4.2**: almacena() - usando persist()
- **MO4.3**: elimina() - usando remove()
- **MO4.4**: modifica() - usando merge()
- **MO4.5**: restauraArtistas() - inicializa colección LAZY fuera de sesión
- **MO4.6.a**: contarConciertosPorArtista() - **INNER JOIN dinámico**
- **MO4.6.b**: buscarConciertosConOSinArtistas() - **OUTER JOIN dinámico**

#### ArtistaDaoJPA
- **MO4.1**: recuperaPorCodigo() - consulta estática JPQL
- **MO4.2**: almacena() - usando persist()
- **MO4.3**: elimina() - usando remove()
- **MO4.4**: modifica() - usando merge()
- **MO4.6.c**: buscarArtistasConMasConciertosDeLaMedia() - **SUBCONSULTA**
- **MO4.6.d**: contarArtistasDelGenero() - **FUNCIÓN DE AGREGACIÓN (COUNT)**

### 4. TESTS JUNIT (P01_EventosMusicales)

**Test01** - Recuperación por clave natural:
- test01_RecuperaPorCodigoConcierto()
- test02_RecuperaPorCodigoArtista()

**Test02** - Alta de objetos:
- test03_AlmacenaConcierto()
- test04_AlmacenaArtista()

**Test03** - Eliminación:
- test05_EliminaConcierto()
- test06_EliminaArtista()

**Test04** - Modificación:
- test07_ModificaConcierto()
- test08_ModificaArtista()

**Test05** - Inicialización LAZY:
- test09_InicializacionLazy() - demuestra restauraArtistas()

**Test06** - Estrategia EAGER:
- test10_EstrategiaEager() - demuestra carga automática de conciertos en Artista

**Test07** - Propagación CASCADE:
- test11_PropagacionCascade() - demuestra propagación de persist

**Test08** - Consultas JPQL:
- test12_ConsultasJPQL() - prueba las 4 consultas (INNER JOIN, OUTER JOIN, subconsulta, agregación)

**Extra**:
- test13_FuncionHash() - verifica el método funcionhash()

### 5. CONSULTAS JPQL IMPLEMENTADAS

1. **INNER JOIN**: Contar conciertos de un artista específico
   ```jpql
   SELECT COUNT(c) FROM Concierto c JOIN c.artistas a WHERE a.nombre = :nombre
   ```

2. **OUTER JOIN**: Buscar todos los conciertos con o sin artistas
   ```jpql
   SELECT DISTINCT c FROM Concierto c LEFT JOIN c.artistas a
   ```

3. **SUBCONSULTA**: Artistas con más conciertos que la media
   ```jpql
   SELECT a FROM Artista a WHERE SIZE(a.conciertos) > 
   (SELECT AVG(SIZE(a2.conciertos)) FROM Artista a2)
   ```

4. **AGREGACIÓN**: Contar artistas de un género
   ```jpql
   SELECT COUNT(a) FROM Artista a WHERE a.genero = :genero
   ```

### 6. ARCHIVOS MODIFICADOS/CREADOS

**Modelo**:
- EventoMusical.java (nueva)
- Concierto.java (nueva)
- Festival.java (nueva)
- Artista.java (nueva)

**DAOs**:
- EventoMusicalDao.java (interface)
- ConciertoDao.java (interface)
- ConciertoDaoJPA.java (implementación)
- ArtistaDao.java (interface)
- ArtistaDaoJPA.java (implementación)

**Tests**:
- P01_EventosMusicales.java (nueva)
- ProductorDatosProba.java (modificada)
- AllTests.java (actualizada)

**Configuración**:
- persistence.xml (actualizado con nuevas entidades)

### 7. CUMPLIMIENTO DE REQUISITOS

✅ Todas las clases con identificador Long y @TableGenerator
✅ Asociación bidireccional implementada (Concierto-Artista)
✅ equals() y hashCode() con clave natural en todas las clases
✅ funcionhash() implementado en todas las clases
✅ Estrategia de herencia JOINED configurada
✅ Mínimo una asociación EAGER (Artista.conciertos)
✅ Mínimo una asociación LAZY (Concierto.artistas)
✅ Propagación CASCADE configurada (Concierto-Artista)
✅ Métodos DAO completos (CRUD + consultas)
✅ Consultas JPQL: estáticas y dinámicas
✅ 4 tipos de consultas: INNER JOIN, OUTER JOIN, subconsulta, agregación
✅ Tests completos (8 tests principales + extras)
✅ API JPA estándar (sin API nativa de Hibernate)

### 8. COMPILACIÓN

El proyecto compila exitosamente sin errores:
```
[INFO] BUILD SUCCESS
[INFO] Compiling 18 source files
```

### 9. PRÓXIMOS PASOS

Para ejecutar los tests:
```bash
mvn test
```

Para ejecutar la aplicación:
```bash
mvn exec:java -Dexec.mainClass="gei.id.tutelado.App"
```

---

**NOTA**: El código mantiene la estructura original del proyecto pero implementa
el dominio de eventos musicales según el diagrama UML proporcionado.
