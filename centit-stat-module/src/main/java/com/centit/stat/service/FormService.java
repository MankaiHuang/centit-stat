package com.centit.stat.service;

import com.centit.stat.po.FormModel;

public interface FormService {
    void createForm(FormModel formModel);

    void updateForm(FormModel formModel);

    void deleteForm(String formId);

    FormModel getForm(String formId);
}
