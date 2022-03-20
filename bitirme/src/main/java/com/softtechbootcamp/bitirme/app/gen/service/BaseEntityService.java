package com.softtechbootcamp.bitirme.app.gen.service;

import com.softtechbootcamp.bitirme.app.gen.entity.BaseAdditionalFields;
import com.softtechbootcamp.bitirme.app.gen.entity.BaseEntity;
import com.softtechbootcamp.bitirme.app.gen.enums.GeneralErrorMessage;
import com.softtechbootcamp.bitirme.app.gen.exceptions.EntityNotFoundExceptions;
import com.softtechbootcamp.bitirme.app.sec.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public abstract  class BaseEntityService<E extends BaseEntity, R extends JpaRepository<E, Long>> {

    private final R eRepository;

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public E save(E e){
        setAdditionalFields(e);

        E savedEntity = eRepository.save(e);

        String simpleEntityName = getBaseEntityName();
        String logInfoMessage = simpleEntityName + " is saved";
        log.info(logInfoMessage);

        return savedEntity;
    }

    public E findByIdWithControl(Long id){

        return eRepository.findById(id).orElseThrow(() -> new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND));
    }

    public List<E> findAllWithControl(){
        List<E> entityList = eRepository.findAll();
        if (entityList.isEmpty()){
            throw  new EntityNotFoundExceptions(GeneralErrorMessage.ENTITIES_NOT_FOUND);
        }
        return entityList;
    }

    public boolean existsByIdWithControl(Long id){
        boolean isExist = eRepository.existsById(id);
        if (!isExist){
            throw new EntityNotFoundExceptions(GeneralErrorMessage.ENTITY_NOT_FOUND);
        }
        return true;
    }

    public void deleteByIdWithControl(Long id){
        E entity = findByIdWithControl(id);

        eRepository.delete(entity);

        String simpleEntityName = getBaseEntityName();
        String logInfoMessage = simpleEntityName + " is deleted at";
        log.info(logInfoMessage);
    }

    public R getRepository() {
        return eRepository;
    }

    private void setAdditionalFields(E entity) {

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        Long currentUserId = getCurrentUserId();

        if (currentUserId == null){
            currentUserId = 1L;
        }

        if (baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if (entity.getId() == null){
            baseAdditionalFields.setCreateDate(new Date());
            baseAdditionalFields.setCreatedBy(currentUserId);
        }

        baseAdditionalFields.setUpdateDate(new Date());
        baseAdditionalFields.setUpdatedBy(currentUserId);

    }

    private String getBaseEntityName(){
        String simpleName = "";
        try {
            simpleName = Class.forName(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName()).getSimpleName();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  simpleName;
    }

    private Long getCurrentUserId() {
        Long currentCustomerId = authenticationService.getCurrentUserId();
        return currentCustomerId;
    }

}
