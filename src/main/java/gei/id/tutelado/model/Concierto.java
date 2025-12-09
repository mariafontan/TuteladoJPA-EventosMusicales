package gei.id.tutelado.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "concierto")
public class Concierto extends EventoMusical implements Serializable {

    @Column(nullable = false)
    private Integer aforo;

    // Asociación bidireccional con Artista (LAZY con CASCADE)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "concierto_artista",
               joinColumns = @JoinColumn(name = "concierto_id"),
               inverseJoinColumns = @JoinColumn(name = "artista_id"))
    private Set<Artista> artistas = new HashSet<>();

    // Constructores
    public Concierto() {
        super();
    }

    public Concierto(Integer codigo_evento, String nombre, LocalDateTime fecha, String lugar, Integer aforo) {
        super(codigo_evento, nombre, fecha, lugar);
        this.aforo = aforo;
    }

    // Getters y Setters
    public Integer getAforo() {
        return aforo;
    }

    public void setAforo(Integer aforo) {
        this.aforo = aforo;
    }

    public Set<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(Set<Artista> artistas) {
        this.artistas = artistas;
    }

    // Métodos de conveniencia para asociación bidireccional
    public void addArtista(Artista artista) {
        this.artistas.add(artista);
        artista.getConciertos().add(this);
    }

    public void removeArtista(Artista artista) {
        this.artistas.remove(artista);
        artista.getConciertos().remove(this);
    }

    @Override
    public String toString() {
        return "Concierto{" +
                "id=" + getId_evento() +
                ", codigo=" + getCodigo_evento() +
                ", nombre='" + getNombre() + '\'' +
                ", aforo=" + aforo +
                ", fecha=" + getFecha() +
                ", lugar='" + getLugar() + '\'' +
                '}';
    }
}
