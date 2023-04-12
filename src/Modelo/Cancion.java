package Modelo;

public class Cancion {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private String anio;
    private String cancionFile;
    private  int albumId;

    public Cancion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }


    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }


    public String getCancionFile() {
        return cancionFile;
    }

    public void setCancionFile(String cancionFile) {
        this.cancionFile = cancionFile;
    }


    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

}//FIN DE LA CLASE CANCIÓN
