package br.org.gam.api.common.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends SoftDeletable, ID> extends JpaRepository<T, ID> {
    void hardDelete(T entity);
    List<T> findAllDeleted();
}
