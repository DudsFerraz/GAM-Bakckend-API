package br.org.gam.api.event.domain;

import java.time.Instant;
import java.util.UUID;

public class Event {
    private UUID id;

    private String title;

    private String description;

    private String location;

    private Instant beginDate;

    private Instant endDate;
}