package za.co.marlonmagonjo.medici.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MultiEntityListener {

    @PrePersist
    public void prePersist(MultiEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(MultiEntity entity) {
        entity.setUpdatedAt(LocalDateTime.now());
    }
}
