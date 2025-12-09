package gei.id.tutelado.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "artista")
@TableGenerator(name = "gen_artista", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val", pkColumnValue = "artista_gen", allocationSize = 1)
public class Artista implements Serializable {

    @Id
    @GeneratedValue(generator = "gen_artista", strategy = GenerationType.TABLE)
    private Long id_artista;

    @Column(nullable = false, unique = true)
    private Integer codigo_artista;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 50)
    private String genero;

    // Asociación bidireccional con Concierto (EAGER)
    @ManyToMany(mappedBy = "artistas", fetch = FetchType.EAGER)
    private Set<Concierto> conciertos = new HashSet<>();

    // Asociación con Festival (LAZY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "festival_artista", joinColumns = @JoinColumn(name = "artista_id"), inverseJoinColumns = @JoinColumn(name = "festival_id"))
    private Set<Festival> festivales = new HashSet<>();

    // Constructores
    public Artista() {
    }

    public Artista(Integer codigo_artista, String nombre, String genero) {
        this.codigo_artista = codigo_artista;
        this.nombre = nombre;
        this.genero = genero;
    }

    // Getters y Setters
    public Long getId_artista() {
        return id_artista;
    }

    public void setId_artista(Long id_artista) {
        this.id_artista = id_artista;
    }

    public Integer getCodigo_artista() {
        return codigo_artista;
    }

    public void setCodigo_artista(Integer codigo_artista) {
        this.codigo_artista = codigo_artista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Set<Concierto> getConciertos() {
        return conciertos;
    }

    public void setConciertos(Set<Concierto> conciertos) {
        this.conciertos = conciertos;
    }

    public Set<Festival> getFestivales() {
        return festivales;
    }

    public void setFestivales(Set<Festival> festivales) {
        this.festivales = festivales;
    }

    // Métodos de conveniencia
    public void addFestival(Festival festival) {
        this.festivales.add(festival);
    }

    public void removeFestival(Festival festival) {
        this.festivales.remove(festival);
    }

    // MO2.1: equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Artista))
            return false;
        Artista artista = (Artista) o;
        return Objects.equals(codigo_artista, artista.codigo_artista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo_artista);
    }

    // MO2.2: funcionhash
    public String funcionhash() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "XDFE34RD39RTF55AK" + ahora.format(formatter);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "id_artista=" + id_artista +
                ", codigo_artista=" + codigo_artista +
                ", nombre='" + nombre + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}
