package br.org.gam.api.common.specification;

public record SpecificationFilter(
        String field,
        Object value,
        ComparationMethodsEnum comparationMethod
) {

    public boolean isValid() {
        if (value == null) return false;
        if (value instanceof String s) return !s.isBlank();

        return true;
    }

}
