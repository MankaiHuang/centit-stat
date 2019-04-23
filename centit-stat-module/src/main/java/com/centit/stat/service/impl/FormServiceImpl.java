package com.centit.stat.service.impl;

import com.centit.stat.dao.FormModelDao;
import com.centit.stat.po.FormModel;
import com.centit.stat.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FormServiceImpl implements FormService {

    @Autowired
    private FormModelDao formModelDao;

    @Override
    public void createForm(FormModel formModel) {
        formModelDao.saveNewObject(formModel);
    }

    @Override
    public void updateForm(FormModel formModel) {
        formModelDao.updateObject(formModel);
    }

    @Override
    public void deleteForm(String formId) {
        formModelDao.deleteObjectById(formId);
    }

    @Override
    @Transactional(readOnly = true)
    public FormModel getForm(String formId) {
        return formModelDao.getObjectById(formId);
    }
}
