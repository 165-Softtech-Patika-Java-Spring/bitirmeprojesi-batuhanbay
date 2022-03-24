package com.softtechbootcamp.bitirme.app.set.service.entityService;

import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.gen.service.BaseEntityService;
import com.softtechbootcamp.bitirme.app.set.dao.SetSettingsRepository;
import com.softtechbootcamp.bitirme.app.set.entity.SetSettings;
import com.softtechbootcamp.bitirme.app.set.enums.SetSettingsErrorMessage;
import org.springframework.stereotype.Service;

@Service
public class SetSettingsEntityService extends BaseEntityService<SetSettings, SetSettingsRepository> {
    public SetSettingsEntityService(SetSettingsRepository eRepository) {
        super(eRepository);
    }

    public SetSettings findByKeyWithControl(String key){
        SetSettings setSettings = getRepository().findByKey(key);
        if (setSettings == null){
            throw new EntityNotFoundExceptions(SetSettingsErrorMessage.SETTINGS_NOT_FOUND);
        }
        return setSettings;
    }
}
