package br.org.gam.api.common.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IBaseRepository<T extends ISoftDeletable, ID> extends JpaRepository<T, ID> {
    void hardDelete(T entity);
    List<T> findAllDeleted();
}
