package com.morlimoore.storedprocedures.services.impl;

import com.morlimoore.storedprocedures.dao.AbstractDao;
import com.morlimoore.storedprocedures.models.AbstractModel;
import com.morlimoore.storedprocedures.models.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T extends AbstractModel> {

    protected AbstractDao<T> dao;

    public AbstractService(AbstractDao<T> dao) {
        this.dao = dao;
    }

    @Transactional
    public T create(T model) {
        return dao.create(model);
    }

    @Transactional
    public void update(T model) {
        dao.update(model);
    }

    @Transactional
    public void delete(Integer id) {
        dao.delete(id);
    }

    public T find(Integer id) {
        return dao.find(id);
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public Page<T> findAll(Integer pageNum, Integer pageSize) {
        return dao.findAll(pageNum, pageSize);
    }


}
