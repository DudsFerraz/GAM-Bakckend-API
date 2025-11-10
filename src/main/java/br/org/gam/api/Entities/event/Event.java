package br.org.gam.api.Entities.event;

import br.org.gam.api.Entities.account.Email;
import br.org.gam.api.Entities.location.Location;
import br.org.gam.api.common.PermissionLevelEnum;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Event {
    private UUID id;
    private String title;
    private String description;
    private Location location;
    private PermissionLevelEnum requiredPermissionLevel;
    private Instant beginDate;
    private Instant endDate;

    /**
     * @deprecated <b>ESTE CONSTRUTOR É EXCLUSIVO PARA USO INTERNO (JPA/MapStruct).</b>
     * <br> <br>
     * <b> Use o método fábrica {@link #create(String title, String description, Location location, PermissionLevelEnum requiredPermissionLevel, Instant beginDate, Instant endDate)}.
     */
    @Deprecated
    Event(UUID id, String title, String description, Location location, Instant beginDate, Instant endDate, PermissionLevelEnum requiredPermissionLevel) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.requiredPermissionLevel = requiredPermissionLevel;
    }

    private Event(String title, String description, Location location, PermissionLevelEnum requiredPermissionLevel, Instant beginDate, Instant endDate) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.requiredPermissionLevel = requiredPermissionLevel;
    }

    public static Event create(String title, String description, Location location, PermissionLevelEnum requiredPermissionLevel, Instant beginDate, Instant endDate) {
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(beginDate, "Begin date cannot be null");
        Objects.requireNonNull(endDate, "End date cannot be null");
        if (beginDate.isAfter(endDate)) throw new IllegalArgumentException("A data final deve ser após a data inicial.");

        String cleanTitle = title.trim();

        return new Event(cleanTitle, description, location, requiredPermissionLevel, beginDate, endDate);
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

    public PermissionLevelEnum getRequiredPermissionLevel() {
        return requiredPermissionLevel;
    }
}