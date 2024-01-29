import java.util.List;

public class Contato {

    private static long proximoId = 1;
    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    public Contato(Long id, String nome, String sobreNome, List<Telefone> telefones) {
        this.id = proximoId++;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }

    // Construtor para novo contato sem ID
    public Contato(String nome, String sobreNome, List<Telefone> telefones) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
}

