
package com.capstone.parismarketbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentId;

    @Column(nullable = false)
    private String marketName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private boolean isLike;

    @Column(nullable = false)
    private String loginId;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId")
    private User user;
}
