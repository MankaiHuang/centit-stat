package com.centit.stat.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.centit.framework.core.dao.CodeBook;
import com.centit.framework.hibernate.dao.BaseDaoImpl;
import com.centit.framework.hibernate.dao.DatabaseOptUtils;
import com.centit.stat.po.QueryModel;
@Repository
public class QueryModelDao extends BaseDaoImpl<QueryModel,String>
{
	public static final Log log = LogFactory.getLog(QueryModelDao.class);
	@Override	
	public Map<String, String> getFilterField() {
		if( filterField == null){
			filterField = new HashMap<String, String>();

			filterField.put("modelName" , CodeBook.EQUAL_HQL_ID);


			filterField.put("modelType" , CodeBook.LIKE_HQL_ID);

			filterField.put("ownerType" , CodeBook.LIKE_HQL_ID);

			filterField.put("ownerCode" , CodeBook.LIKE_HQL_ID);

			filterField.put("querySql" , CodeBook.LIKE_HQL_ID);

			filterField.put("queryDesc" , CodeBook.LIKE_HQL_ID);

			filterField.put("formNameFormat" , CodeBook.LIKE_HQL_ID);

			filterField.put("resultName" , CodeBook.LIKE_HQL_ID);

			filterField.put("rowDrawChart" , CodeBook.LIKE_HQL_ID);

			filterField.put("drawChartBeginCol" , CodeBook.LIKE_HQL_ID);

			filterField.put("drawChartEndCol" , CodeBook.LIKE_HQL_ID);

			filterField.put("additionRow" , CodeBook.LIKE_HQL_ID);

			filterField.put("rowLogic" , CodeBook.LIKE_HQL_ID);

			filterField.put("rowLogicValue" , CodeBook.LIKE_HQL_ID);

		}
		return filterField;
	} 

	public String  getWizardNo(){
        return DatabaseOptUtils.getNextValueOfSequence(this,"S_WIZARDNO");
    }
		
}
