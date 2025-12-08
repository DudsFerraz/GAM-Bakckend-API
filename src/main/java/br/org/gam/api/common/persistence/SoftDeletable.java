package br.org.gam.api.common.persistence;


import java.time.Instant;
import java.util.UUID;

public interface SoftDeletable {
    void setDeletedAt(Instant deletedAt);
    void setDeletedBy(UUID deletedBy);
}
