package br.org.gam.api.common.auditing;

import org.mapstruct.Mapping;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@Mapping(target = "createdAt", ignore = true)
@Mapping(target = "createdBy", ignore = true)
@Mapping(target = "deletedAt", ignore = true)
@Mapping(target = "deletedBy", ignore = true)
public @interface IgnoreJunctionAuditFields {
}
