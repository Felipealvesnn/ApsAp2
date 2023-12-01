package entidades;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String descricao;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Column
    private Integer v1;

    @Column
    private Integer v2;

    @Column
    private Integer v3;

    @Column
    private Integer v4;

    @Column
    private Integer v5;

    @Column
    private Integer v6;

    @Column
    private Integer v7;

    @Column
    private Integer v8;

    @Column
    private Integer v9;

    @Column
    private Integer v10;

    // Construtor
    public Jogo() {
        // Construtor vazio necessário para JPA
    }

    // Construtor com parâmetros
    public Jogo(String descricao, Date dataCriacao, Integer v1, Integer v2, Integer v3, Integer v4, Integer v5, Integer v6, Integer v7, Integer v8, Integer v9, Integer v10) {
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
        this.v5 = v5;
        this.v6 = v6;
        this.v7 = v7;
        this.v8 = v8;
        this.v9 = v9;
        this.v10 = v10;
    }

    
    public String getConcatenatedValues() {
        return String.format("%d, %d, %d, %d, %d, %d, %d, %d, %d, %d",
                getV1(), getV2(), getV3(), getV4(), getV5(),
                getV6(), getV7(), getV8(), getV9(), getV10());
    }
    
    public String getConcatenatedEvenValues() {
        List<Integer> evenValues = Arrays.asList(getV1(), getV2(), getV3(), getV4(), getV5(),
                getV6(), getV7(), getV8(), getV9(), getV10());

        List<Integer> evenValuesList = evenValues.stream()
                .filter(value -> value != null && value % 2 == 0)
                .collect(Collectors.toList());

        return evenValuesList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    
    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getV1() {
        return v1;
    }

    public void setV1(Integer v1) {
        this.v1 = v1;
    }

    public Integer getV2() {
        return v2;
    }

    public void setV2(Integer v2) {
        this.v2 = v2;
    }

    public Integer getV3() {
        return v3;
    }

    public void setV3(Integer v3) {
        this.v3 = v3;
    }

    public Integer getV4() {
        return v4;
    }

    public void setV4(Integer v4) {
        this.v4 = v4;
    }

    public Integer getV5() {
        return v5;
    }

    public void setV5(Integer v5) {
        this.v5 = v5;
    }

    public Integer getV6() {
        return v6;
    }

    public void setV6(Integer v6) {
        this.v6 = v6;
    }

    public Integer getV7() {
        return v7;
    }

    public void setV7(Integer v7) {
        this.v7 = v7;
    }

    public Integer getV8() {
        return v8;
    }

    public void setV8(Integer v8) {
        this.v8 = v8;
    }

    public Integer getV9() {
        return v9;
    }

    public void setV9(Integer v9) {
        this.v9 = v9;
    }

    public Integer getV10() {
        return v10;
    }

    public void setV10(Integer v10) {
        this.v10 = v10;
    }
}
