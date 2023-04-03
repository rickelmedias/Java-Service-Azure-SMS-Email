package dev.rickelmedias.apismsemail.entities;

import dev.rickelmedias.apismsemail.enums.PinTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "users_pin")
@Entity(name = "UserPin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int pin;

    @Column(name = "generated_pins", nullable = false)
    private int gerneratedPins;

    @Enumerated(EnumType.STRING)
    private PinTypeEnum type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }
    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }
    @PreRemove
    protected void preRemove() {
        this.updatedAt = new Date();
    }
}
