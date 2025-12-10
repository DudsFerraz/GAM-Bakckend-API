package br.org.gam.api.Entities.event;

import br.org.gam.api.Entities.RBAC.permission.Permission;
import br.org.gam.api.Entities.RBAC.permission.PermissionMapper;
import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.common.persistence.UUIDGenerator;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Event {
    private UUID id;
    private String title;
    private String description;
    private Location location;
    private Permission requiredPermission;
    private Instant beginDate;
    private Instant endDate;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO E JPA/MapStruct.</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #register(String title, String description, Location location, Permission requiredPermission, Instant beginDate, Instant endDate)}.
     */
    @Deprecated
    Event(UUID id, String title, String description, Location location, Permission requiredPermission, Instant beginDate, Instant endDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.requiredPermission = requiredPermission;
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public static Event register(String title, String description, Location location, Permission requiredPermission, Instant beginDate, Instant endDate) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(beginDate, "Begin date cannot be null");
        Objects.requireNonNull(endDate, "End date cannot be null");
        if (!endDate.isAfter(beginDate)) throw new IllegalArgumentException("endDate must be after beginDate.");

        String cleanTitle = title.trim();
        String cleanDescription = description.trim();

        UUID id = UUIDGenerator.generateUUIDV7();

        return new Event(id, cleanTitle, cleanDescription, location, requiredPermission ,beginDate, endDate);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public Instant getBeginDate() {
        return beginDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Permission getRequiredPermission() {
        return requiredPermission;
    }
}