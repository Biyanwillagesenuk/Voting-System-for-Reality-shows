package com.votingsystem.for_reality_shows.repository;

import com.votingsystem.for_reality_shows.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show, Long> {
}