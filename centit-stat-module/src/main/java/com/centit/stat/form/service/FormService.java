package com.centit.stat.form.service;

import com.centit.stat.form.po.FormModel;

public interface FormService {
    void createForm(FormModel formModel);

    void updateForm(FormModel formModel);

    void deleteForm(String formId);

    FormModel getForm(String formId);
}
