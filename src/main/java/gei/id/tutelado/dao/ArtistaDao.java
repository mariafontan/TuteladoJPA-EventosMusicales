package gei.id.tutelado.dao;

import gei.id.tutelado.model.Artista;

public interface ArtistaDao {
    
    // MO4.1: Recuperación por clave natural
    Artista recuperaPorCodigo(Integer codigo_artista);
    
    // MO4.2: Alta
    void almacena(Artista artista);
    
    // MO4.3: Eliminación
    void elimina(Artista artista);
    
    // MO4.4: Actualización
    Artista modifica(Artista artista);
    
    // MO4.6.c: Consulta con subconsulta
    java.util.List<Artista> buscarArtistasConMasConciertosDeLaMedia();
    
    // MO4.6.d: Consulta con función de agregación
    Long contarArtistasDelGenero(String genero);
    
    void setup(Object config);
}
