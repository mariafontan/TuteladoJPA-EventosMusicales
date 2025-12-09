package gei.id.tutelado.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "festival")
public class Festival extends EventoMusical implements Serializable {

    @Column(nullable = false)
    private Integer num_dias;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "festival_estilos", joinColumns = @JoinColumn(name = "festival_id"))
    @Column(name = "estilo")
    private Set<String> estilos = new HashSet<>();

    // Asociación con Artista
    @ManyToMany(mappedBy = "festivales", fetch = FetchType.LAZY)
    private Set<Artista> artistas = new HashSet<>();

    // Constructores
    public Festival() {
        super();
    }

    public Festival(Integer codigo_evento, String nombre, LocalDateTime fecha, String lugar, Integer num_dias) {
        super(codigo_evento, nombre, fecha, lugar);
        this.num_dias = num_dias;
    }

    // Getters y Setters
    public Integer getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(Integer num_dias) {
        this.num_dias = num_dias;
    }

    public Set<String> getEstilos() {
        return estilos;
    }

    public void setEstilos(Set<String> estilos) {
        this.estilos = estilos;
    }

    public Set<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(Set<Artista> artistas) {
        this.artistas = artistas;
    }

    // Métodos de conveniencia
    public void addEstilo(String estilo) {
        this.estilos.add(estilo);
    }

    public void removeEstilo(String estilo) {
        this.estilos.remove(estilo);
    }

    public void addArtista(Artista artista) {
        this.artistas.add(artista);
        artista.getFestivales().add(this);
    }

    public void removeArtista(Artista artista) {
        this.artistas.remove(artista);
        artista.getFestivales().remove(this);
    }

    @Override
    public String toString() {
        return "Festival{" +
                "id=" + getId_evento() +
                ", codigo=" + getCodigo_evento() +
                ", nombre='" + getNombre() + '\'' +
                ", num_dias=" + num_dias +
                ", fecha=" + getFecha() +
                ", lugar='" + getLugar() + '\'' +
                '}';
    }
}
