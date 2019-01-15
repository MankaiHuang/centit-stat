package com.centit.stat.form.service.impl;

import com.centit.stat.form.dao.FormModelDao;
import com.centit.stat.form.po.FormModel;
import com.centit.stat.form.service.FormService;
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
