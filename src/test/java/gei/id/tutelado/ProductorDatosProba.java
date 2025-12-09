package gei.id.tutelado;

import java.time.LocalDateTime;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.ArtistaDaoJPA;
import gei.id.tutelado.dao.ConciertoDaoJPA;
import gei.id.tutelado.model.Artista;
import gei.id.tutelado.model.Concierto;
import gei.id.tutelado.model.Festival;

public class ProductorDatosProba {

    private ConciertoDaoJPA conciertoDao;
    private ArtistaDaoJPA artistaDao;

    // Datos de prueba públicos
    public Artista artista1;
    public Artista artista2;
    public Artista artista3;
    public Concierto concierto1;
    public Concierto concierto2;
    public Festival festival1;

    public void Setup(Configuracion config) {
        conciertoDao = new ConciertoDaoJPA();
        conciertoDao.setup(config.get("EMF"));
        
        artistaDao = new ArtistaDaoJPA();
        artistaDao.setup(config.get("EMF"));
    }

    public void creaObxectosProba() {
        
        // Crear artistas
        artista1 = new Artista(1001, "The Beatles", "Rock");
        artista2 = new Artista(1002, "Pink Floyd", "Rock Progresivo");
        artista3 = new Artista(1003, "Daft Punk", "Electrónica");
        
        artistaDao.almacena(artista1);
        artistaDao.almacena(artista2);
        artistaDao.almacena(artista3);

        // Crear conciertos
        concierto1 = new Concierto(2001, "Concierto Rock Legends", 
                                   LocalDateTime.of(2025, 6, 15, 20, 0), 
                                   "Madison Square Garden", 15000);
        concierto1.addArtista(artista1);
        concierto1.addArtista(artista2);
        
        concierto2 = new Concierto(2002, "Electronic Night", 
                                   LocalDateTime.of(2025, 7, 20, 22, 0), 
                                   "Berghain Berlin", 8000);
        concierto2.addArtista(artista3);
        
        conciertoDao.almacena(concierto1);
        conciertoDao.almacena(concierto2);

        // Crear festival
        festival1 = new Festival(3001, "Summer Festival 2025", 
                                LocalDateTime.of(2025, 8, 10, 18, 0), 
                                "Hyde Park London", 3);
        festival1.addEstilo("Rock");
        festival1.addEstilo("Pop");
        festival1.addEstilo("Electrónica");
        festival1.addArtista(artista1);
        festival1.addArtista(artista2);
        festival1.addArtista(artista3);
    }

    public void limpaBD() {
        // Limpieza de datos de prueba si es necesario
    }
}
