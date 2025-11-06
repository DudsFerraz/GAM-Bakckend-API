package br.org.gam.api.Entities.account.services.changePermissionLevel;

import java.util.UUID;

public interface IChangePermissionLevelService {
    public void setToVisitor(UUID accountId);
    public void setToMember(UUID accountId);
    public void setToCoord(UUID accountId);
}
