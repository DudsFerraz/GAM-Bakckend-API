package br.org.gam.api.shared.domain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GamEmailConverter implements Converter<String, GamEmail> {
    @Override
    public GamEmail convert(String source) {
        return GamEmail.of(source);
    }
}
