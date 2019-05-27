package com.centit.stat.service;

public interface FormService {
    void createForm(FormModel formModel);

    void updateForm(FormModel formModel);

    void deleteForm(String formId);

    FormModel getForm(String formId);
}
