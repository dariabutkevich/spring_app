package com.example.pro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lot")
@EntityListeners(AuditingEntityListener.class)
public class Lot {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lotname", nullable = false)
    private String lotname;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "startprice", nullable = false)
    private BigDecimal startprice;

    @Column(name = "currentprice", nullable = false)
    private BigDecimal currentprice;

    @Column(name = "lastuser", nullable = false)
    private String lastuser;

    @Column(name = "photo", nullable = false)
    private String photo;

    @Column(name = "starttime", nullable = false)
    private LocalDateTime starttime;

    @Column(name = "endtime", nullable = false)
    private LocalDateTime endtime;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLotname(String lotname) {
        this.lotname = lotname;
    }

    public String getLotname() {
        return lotname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartprice(BigDecimal startprice) {
        this.startprice = startprice;
    }

    public BigDecimal getStartprice() {
        return startprice;
    }

    public void setCurrentprice(BigDecimal currentprice) {
        this.currentprice = currentprice;
    }

    public BigDecimal getCurrentprice() {
        return currentprice;
    }

    public void setLastuser(String lastuser) {
        this.lastuser = lastuser;
    }

    public String getLastuser() {
        return lastuser;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }
}


