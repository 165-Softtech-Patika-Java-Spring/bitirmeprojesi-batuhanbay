package com.softtechbootcamp.bitirme.app.set.dao;

import com.softtechbootcamp.bitirme.app.set.entity.SetSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetSettingsRepository extends JpaRepository<SetSettings, Long> {
    SetSettings findByKey(String key);
}
