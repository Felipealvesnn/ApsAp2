package entidades;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Jogo  implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_partida")
    private Date dataPartida;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    @Column(name = "time1")
    private String time1;

    @Column(name = "time2")
    private String time2;

    @Column(name = "gols_time1")
    private Integer golsTime1;

    @Column(name = "gols_time2")
    private Integer golsTime2;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    // Construtor padrão
    public Jogo() {
    }

    // Construtor com parâmetros
    public Jogo(Date dataPartida, Date dataCadastro, String time1, String time2, Integer golsTime1, Integer golsTime2) {
        this.dataPartida = dataPartida;
        this.dataCadastro = dataCadastro;
        this.time1 = time1;
        this.time2 = time2;
        this.golsTime1 = golsTime1;
        this.golsTime2 = golsTime2;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public Integer getGolsTime1() {
        return golsTime1;
    }

    public void setGolsTime1(Integer golsTime1) {
        this.golsTime1 = golsTime1;
    }

    public Integer getGolsTime2() {
        return golsTime2;
    }

    public void setGolsTime2(Integer golsTime2) {
        this.golsTime2 = golsTime2;
    }
}