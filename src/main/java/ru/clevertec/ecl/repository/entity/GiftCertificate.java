package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "gift_certificate")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "name cannot be empty")
    @Size(min = 2, max = 20, message = "message should be between 2 to 20")
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    @Column(name = "create_date")
    private ZonedDateTime createDate;
    @Column(name = "last_update_date")
    private ZonedDateTime lastUpdateDate;
    @ManyToMany
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();
}
