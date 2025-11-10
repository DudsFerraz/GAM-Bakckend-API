package br.org.gam.api.Entities.presence;

import br.org.gam.api.Entities.event.IEventMapper;
import br.org.gam.api.Entities.member.IMemberMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IMemberMapper.class, IEventMapper.class})
public interface IPresenceMapper {
}
