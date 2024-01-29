public class Telefone {
    private static long proximoId = 1;
    private Long id;
    private String ddd;
    private Long numero;

    public Telefone(String ddd, Long numero) {
        this.id = proximoId++;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
}
