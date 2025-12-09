package gei.id.tutelado.dao;

import gei.id.tutelado.model.Concierto;

public interface ConciertoDao {
    
    // MO4.1: Recuperación por clave natural
    Concierto recuperaPorCodigo(Integer codigo_evento);
    
    // MO4.2: Alta
    void almacena(Concierto concierto);
    
    // MO4.3: Eliminación
    void elimina(Concierto concierto);
    
    // MO4.4: Actualización
    Concierto modifica(Concierto concierto);
    
    // MO4.5: Inicializar colección LAZY
    Concierto restauraArtistas(Concierto concierto);
    
    // MO4.6.a: Consulta con INNER JOIN
    Long contarConciertosPorArtista(String nombreArtista);
    
    // MO4.6.b: Consulta con OUTER JOIN
    java.util.List<Concierto> buscarConciertosConOSinArtistas();
    
    void setup(Object config);
}
