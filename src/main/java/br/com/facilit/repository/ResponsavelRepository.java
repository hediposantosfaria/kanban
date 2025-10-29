package br.com.facilit.repository;

import br.com.facilit.domain.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    Optional<Responsavel> findByEmail(String email);

    boolean existsByEmail(String email);
}
