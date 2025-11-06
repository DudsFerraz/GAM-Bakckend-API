package br.org.gam.api.Entities.member.services.activation;

import java.util.UUID;

public interface IActivationService {
    public void activate(UUID memberId);
    public void deactivate(UUID memberId);
}
