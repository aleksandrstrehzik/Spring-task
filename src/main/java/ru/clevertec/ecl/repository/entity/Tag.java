package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
