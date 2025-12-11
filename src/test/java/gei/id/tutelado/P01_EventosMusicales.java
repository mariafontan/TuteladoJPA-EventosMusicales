package gei.id.tutelado;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.MethodSorters;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.dao.ArtistaDaoJPA;
import gei.id.tutelado.dao.ConciertoDaoJPA;
import gei.id.tutelado.model.Artista;
import gei.id.tutelado.model.Concierto;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class P01_EventosMusicales {

    private static ProductorDatosProba produtorDatos = new ProductorDatosProba();
    
    private static Configuracion cfg;
    private static ConciertoDaoJPA conciertoDao;
    private static ArtistaDaoJPA artistaDao;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            System.out.println("\n\nIniciando test: " + description.getMethodName());
        }
    };

    @BeforeClass
    public static void setupClass() {
        cfg = new ConfiguracionJPA();
        cfg.start();

        conciertoDao = new ConciertoDaoJPA();
        conciertoDao.setup(cfg.get("EMF"));
        
        artistaDao = new ArtistaDaoJPA();
        artistaDao.setup(cfg.get("EMF"));

        produtorDatos.Setup(cfg);
        produtorDatos.creaObxectosProba();
    }

    @AfterClass
    public static void tearDownClass() {
        cfg.endUp();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // Test01: Recuperación por clave natural
    @Test
    public void test01_RecuperaPorCodigoConcierto() {
        Concierto concierto;

        System.out.println("\n\tRecuperando concierto por código...");
        concierto = conciertoDao.recuperaPorCodigo(2001);
        
        assertNotNull("Concierto no encontrado", concierto);
        assertEquals("Código incorrecto", Integer.valueOf(2001), concierto.getCodigo_evento());
        assertEquals("Nombre incorrecto", "Concierto Rock Legends", concierto.getNombre());
        assertEquals("Aforo incorrecto", Integer.valueOf(15000), concierto.getAforo());
        
        System.out.println("\t\tConcierto recuperado: " + concierto);
    }

    @Test
    public void test02_RecuperaPorCodigoArtista() {
        Artista artista;

        System.out.println("\n\tRecuperando artista por código...");
        artista = artistaDao.recuperaPorCodigo(1001);
        
        assertNotNull("Artista no encontrado", artista);
        assertEquals("Código incorrecto", Integer.valueOf(1001), artista.getCodigo_artista());
        assertEquals("Nombre incorrecto", "The Beatles", artista.getNombre());
        assertEquals("Género incorrecto", "Rock", artista.getGenero());
        
        System.out.println("\t\tArtista recuperado: " + artista);
    }

    // Test02: Alta de objetos
    @Test
    public void test03_AlmacenaConcierto() {
        Concierto concierto;

        System.out.println("\n\tAlmacenando nuevo concierto...");
        concierto = new Concierto(2003, "Jazz Night", 
                                  java.time.LocalDateTime.of(2025, 9, 1, 21, 0),
                                  "Blue Note NYC", 500);
        conciertoDao.almacena(concierto);
        
        System.out.println("\t\tConcierto almacenado, recuperándolo...");
        Concierto recuperado = conciertoDao.recuperaPorCodigo(2003);
        
        assertNotNull("Concierto no recuperado", recuperado);
        assertEquals("Nombre incorrecto", "Jazz Night", recuperado.getNombre());
        System.out.println("\t\tConcierto recuperado: " + recuperado);
    }

    @Test
    public void test04_AlmacenaArtista() {
        Artista artista;

        System.out.println("\n\tAlmacenando nuevo artista...");
        artista = new Artista(1004, "Miles Davis", "Jazz");
        artistaDao.almacena(artista);
        
        System.out.println("\t\tArtista almacenado, recuperándolo...");
        Artista recuperado = artistaDao.recuperaPorCodigo(1004);
        
        assertNotNull("Artista no recuperado", recuperado);
        assertEquals("Nombre incorrecto", "Miles Davis", recuperado.getNombre());
        System.out.println("\t\tArtista recuperado: " + recuperado);
    }

    // Test03: Eliminación
    @Test
    public void test05_EliminaConcierto() {
        Concierto concierto;

        System.out.println("\n\tCreando concierto temporal...");
        concierto = new Concierto(2099, "Concierto Temporal", 
                                  java.time.LocalDateTime.now(),
                                  "Lugar Temporal", 100);
        conciertoDao.almacena(concierto);
        
        System.out.println("\t\tEliminando concierto...");
        conciertoDao.elimina(concierto);
        
        System.out.println("\t\tConcierto eliminado correctamente");
    }

    @Test
    public void test06_EliminaArtista() {
        Artista artista;

        System.out.println("\n\tCreando artista temporal...");
        artista = new Artista(1099, "Artista Temporal", "Temporal");
        artistaDao.almacena(artista);
        
        System.out.println("\t\tEliminando artista...");
        artistaDao.elimina(artista);
        
        System.out.println("\t\tArtista eliminado correctamente");
    }

    // Test04: Modificación
    @Test
    public void test07_ModificaConcierto() {
        Concierto concierto;

        System.out.println("\n\tRecuperando concierto para modificar...");
        concierto = conciertoDao.recuperaPorCodigo(2001);
        
        System.out.println("\t\tModificando aforo...");
        concierto.setAforo(20000);
        conciertoDao.modifica(concierto);
        
        System.out.println("\t\tRecuperando concierto modificado...");
        Concierto modificado = conciertoDao.recuperaPorCodigo(2001);
        
        assertEquals("Aforo no modificado", Integer.valueOf(20000), modificado.getAforo());
        System.out.println("\t\tConcierto modificado: " + modificado);
    }

    @Test
    public void test08_ModificaArtista() {
        Artista artista;

        System.out.println("\n\tRecuperando artista para modificar...");
        artista = artistaDao.recuperaPorCodigo(1002);
        
        System.out.println("\t\tModificando género...");
        artista.setGenero("Progressive Rock");
        artistaDao.modifica(artista);
        
        System.out.println("\t\tRecuperando artista modificado...");
        Artista modificado = artistaDao.recuperaPorCodigo(1002);
        
        assertEquals("Género no modificado", "Progressive Rock", modificado.getGenero());
        System.out.println("\t\tArtista modificado: " + modificado);
    }

    // Test05: Inicialización LAZY
    @Test
    public void test09_InicializacionLazy() {
        Concierto concierto;

        System.out.println("\n\tRecuperando concierto...");
        concierto = conciertoDao.recuperaPorCodigo(2001);
        
        System.out.println("\t\tForzando inicialización de artistas LAZY...");
        Concierto conciertoConArtistas = conciertoDao.restauraArtistas(concierto);
        
        assertNotNull("Artistas no inicializados", conciertoConArtistas.getArtistas());
        assertTrue("Artistas vacíos", conciertoConArtistas.getArtistas().size() > 0);
        System.out.println("\t\tArtistas inicializados: " + conciertoConArtistas.getArtistas().size());
    }

    // Test06: Estrategia EAGER
    @Test
    public void test10_EstrategiaEager() {
        Artista artista;

        System.out.println("\n\tRecuperando artista (conciertos configurados como EAGER)...");
        artista = artistaDao.recuperaPorCodigo(1001);
        
        assertNotNull("Conciertos no cargados", artista.getConciertos());
        System.out.println("\t\tConciertos cargados automáticamente: " + artista.getConciertos().size());
    }

    // Test07: Propagación automática (CASCADE)
    @Test
    public void test11_PropagacionCascade() {
        Concierto concierto;
        Artista nuevoArtista;

        System.out.println("\n\tCreando concierto con nuevo artista (probando CASCADE)...");
        concierto = new Concierto(2050, "Concierto con Cascade", 
                                  java.time.LocalDateTime.now(),
                                  "Lugar Cascade", 5000);
        
        nuevoArtista = new Artista(1050, "Artista Cascade", "Rock");
        concierto.addArtista(nuevoArtista);
        
        System.out.println("\t\tAlmacenando concierto (debería propagar a artista)...");
        conciertoDao.almacena(concierto);
        
        System.out.println("\t\tRecuperando artista propagado...");
        Artista artistaRecuperado = artistaDao.recuperaPorCodigo(1050);
        
        assertNotNull("Artista no propagado", artistaRecuperado);
        System.out.println("\t\tArtista propagado correctamente: " + artistaRecuperado);
    }

    // Test08: Consultas JPQL
    @Test
    public void test12_ConsultasJPQL() {
        Long count;
        java.util.List<?> lista;

        System.out.println("\n\tProbando consultas JPQL...");
        
        // INNER JOIN
        System.out.println("\t\tConsulta con INNER JOIN...");
        count = conciertoDao.contarConciertosPorArtista("The Beatles");
        assertTrue("No se encontraron conciertos", count > 0);
        System.out.println("\t\t\tConciertos de The Beatles: " + count);
        
        // OUTER JOIN
        System.out.println("\t\tConsulta con OUTER JOIN...");
        lista = conciertoDao.buscarConciertosConOSinArtistas();
        assertNotNull("Lista nula", lista);
        System.out.println("\t\t\tConciertos encontrados: " + lista.size());
        
        // Subconsulta
        System.out.println("\t\tConsulta con subconsulta...");
        lista = artistaDao.buscarArtistasConMasConciertosDeLaMedia();
        assertNotNull("Lista nula", lista);
        System.out.println("\t\t\tArtistas con más conciertos: " + lista.size());
        
        // Agregación
        System.out.println("\t\tConsulta con agregación...");
        count = artistaDao.contarArtistasDelGenero("Rock");
        assertTrue("No se encontraron artistas de Rock", count > 0);
        System.out.println("\t\t\tArtistas de Rock: " + count);
    }
}
