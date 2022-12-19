package softuni.exam.models.entity;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;

@MappedSuperclass
public abstract class BaseEntity {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
