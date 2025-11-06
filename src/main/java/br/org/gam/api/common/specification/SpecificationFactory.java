package br.org.gam.api.common.specification;

import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import java.util.Collection;
import jakarta.persistence.criteria.Path;

public final class SpecificationFactory {
    private SpecificationFactory() {}

    private static <T> Path<Object> getPath(Root<T> root, String field) {
        String[] parts = field.split("\\.");
        Path<Object> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }

    public static <T> Specification<T> equals(String field, Object value) {
        return (root, query, cb) -> cb.equal(getPath(root, field), value);
    }

    public static <T> Specification<T> like(String field, String value) {
        return (root, query, cb) -> cb.like(cb.lower(getPath(root, field).as(String.class)), "%" + value.toLowerCase() + "%");
    }

    public static <T, C extends Comparable<? super C>> Specification<T> isGreaterThanOrEqual(String field, C value) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(getPath(root, field).as((Class<C>) value.getClass()), value);
    }

    public static <T, C extends Comparable<? super C>> Specification<T> isLessThanOrEqual(String field, C value) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(getPath(root, field).as((Class<C>) value.getClass()), value);
    }

    public static <T> Specification<T> in(String field, Collection<?> values) {
        return (root, query, cb) -> getPath(root, field).in(values);
    }
}