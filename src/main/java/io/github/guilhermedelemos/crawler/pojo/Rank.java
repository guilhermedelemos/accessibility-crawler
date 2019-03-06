package io.github.guilhermedelemos.crawler.pojo;

import javax.persistence.*;

@Entity
@Table(name="rank")
public class Rank {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, sequenceName="rank_id_seq")
    private Long id;
    @Column
    private String name;

    public Rank() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}