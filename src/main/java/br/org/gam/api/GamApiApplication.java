package br.org.gam.api;

import br.org.gam.api.common.persistence.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
		basePackages = "br.org.gam.api",
		repositoryBaseClass = BaseRepositoryImpl.class
)
@EnableJpaAuditing
@SpringBootApplication
public class GamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamApiApplication.class, args);
	}

}
