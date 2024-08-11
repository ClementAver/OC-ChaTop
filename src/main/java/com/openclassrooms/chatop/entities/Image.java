package com.openclassrooms.chatop.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "image_table")
@Getter
@Setter
@Builder
@NoArgsConstructor()
@AllArgsConstructor()
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "picByte", columnDefinition="mediumblob")
    private byte[] picByte;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User users_id;
}
