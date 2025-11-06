package br.org.gam.api.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public record Name(
        @Column(name = "first_name", nullable = false) String firstName,
        @Column(name = "surname", nullable = false) String surname

) implements Serializable {

    public Name {
        Objects.requireNonNull(firstName, "First name cannot be null");
        Objects.requireNonNull(surname, "Last name cannot be null");

        if (firstName.trim().isEmpty() || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("First or last name cannot be blank");
        }

        firstName = firstName.trim();
        surname = surname.trim();
    }

    public String getFullName() {
        return this.firstName + " " + this.surname;
    }
}
