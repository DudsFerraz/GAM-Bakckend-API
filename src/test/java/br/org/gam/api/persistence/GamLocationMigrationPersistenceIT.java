package br.org.gam.api.persistence;

import br.org.gam.api.db.migration.R__SeedPermissionsAndRoles;
import br.org.gam.api.testing.annotation.IntegrationTest;
import br.org.gam.api.testing.annotation.PersistenceTest;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@PersistenceTest
@DisplayName("Persistence - GamLocation schema migration")
class GamLocationMigrationPersistenceIT {

    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:18-alpine")
    );

    static {
        POSTGRES.start();
    }

    private final DataSource dataSource = dataSource();
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    @Test
    @DisplayName("REQ-GAM-LOCATION-007 and ADR-0009 - V22 legacy-row backfill -> accent-insensitive canonical identity")
    void v22ShouldBackfillLegacyRowsWithCanonicalDuplicateIdentity() {
        String schema = uniqueSchema("gam_location_backfill");
        migrate(schema, "21", "classpath:db/migration").migrate();

        UUID id = UUID.randomUUID();
        Timestamp now = Timestamp.from(Instant.now());
        jdbcTemplate.update(
                "INSERT INTO " + schema + ".locations "
                        + "(id, name, street, city, state, postal_code, country_code, created_at, updated_at) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                id,
                "Sal\u00e3o  S\u00e3o Jos\u00e9",
                "Rua S\u00e3o Jos\u00e9, 123",
                "S\u00e3o Paulo",
                "S\u00e3o Paulo",
                "01000-000",
                "BR",
                now,
                now
        );

        migrate(schema, null, "classpath:db/migration").migrate();

        Map<String, Object> identity = jdbcTemplate.queryForMap(
                "SELECT identity_name, identity_street, identity_city, identity_state, "
                        + "identity_postal_code, identity_country_code "
                        + "FROM " + schema + ".gam_locations WHERE id = ?",
                id
        );
        assertThat(identity)
                .containsEntry("identity_name", "salao sao jose")
                .containsEntry("identity_street", "rua sao jose, 123")
                .containsEntry("identity_city", "sao paulo")
                .containsEntry("identity_state", "sao paulo")
                .containsEntry("identity_postal_code", "01000-000")
                .containsEntry("identity_country_code", "br");
    }

    @Test
    @DisplayName("REQ-EVENT-004 - V22 Event schema -> GamLocation foreign key is required")
    void v22ShouldRequireEveryEventToReferenceAGamLocation() {
        String schema = uniqueSchema("event_gam_location_required");
        migrate(schema, null, "classpath:db/migration").migrate();

        String isNullable = jdbcTemplate.queryForObject(
                "SELECT is_nullable FROM information_schema.columns "
                        + "WHERE table_schema = ? AND table_name = 'events' AND column_name = 'gam_location_id'",
                String.class,
                schema
        );

        assertThat(isNullable).isEqualTo("NO");
    }

    @Test
    @DisplayName("REQ-GAM-LOCATION-005 - development Flyway callback -> canonical GamLocation fixtures")
    void developmentCallbackShouldUseCanonicalGamLocationSchema() {
        String schema = uniqueSchema("gam_location_dev");
        Flyway flyway = migrate(
                schema,
                null,
                "classpath:db/migration",
                "classpath:db/dev-migration"
        );

        flyway.migrate();
        flyway.migrate();

        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + schema + ".gam_locations WHERE name IN (?, ?)",
                Long.class,
                "Sede Principal GAM",
                "Sal\u00e3o de Eventos Anexo"
        )).isEqualTo(2L);
        assertThat(jdbcTemplate.queryForMap(
                "SELECT identity_name, identity_street, identity_city, identity_country_code "
                        + "FROM " + schema + ".gam_locations WHERE name = 'Sede Principal GAM'"
        )).containsEntry("identity_name", "sede principal gam")
                .containsEntry("identity_street", "rua ficticia, 123")
                .containsEntry("identity_city", "sao paulo")
                .containsEntry("identity_country_code", "br");
        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + schema + ".events "
                        + "WHERE title IN (?, ?, ?) AND gam_location_id IS NOT NULL",
                Long.class,
                "Reuni\u00e3o de Coordenadores",
                "Encontro Semanal GAM",
                "Palestra sobre Voluntariado (Passado)"
        )).isEqualTo(3L);
        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + schema + ".events WHERE gam_location_id IS NULL",
                Long.class
        )).isZero();
        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM " + schema + ".events "
                        + "WHERE title = 'Evento Portas Abertas' AND gam_location_id IS NOT NULL",
                Long.class
        )).isEqualTo(1L);
    }

    private Flyway migrate(String schema, String target, String... locations) {
        FluentConfiguration configuration = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .defaultSchema(schema)
                .createSchemas(true)
                .locations(locations)
                .javaMigrations(new R__SeedPermissionsAndRoles());
        if (target != null) {
            configuration.target(MigrationVersion.fromVersion(target));
        }
        return configuration.load();
    }

    private static DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(POSTGRES.getJdbcUrl());
        dataSource.setUsername(POSTGRES.getUsername());
        dataSource.setPassword(POSTGRES.getPassword());
        return dataSource;
    }

    private static String uniqueSchema(String prefix) {
        return prefix + "_" + UUID.randomUUID().toString().replace("-", "");
    }
}
