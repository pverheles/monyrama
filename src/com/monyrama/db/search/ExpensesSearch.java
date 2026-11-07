package com.monyrama.db.search;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.monyrama.controller.HibernateUtil;
import com.monyrama.controller.Resultable;
import com.monyrama.entity.PExpense;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;

import com.monyrama.db.search.parameters.ExpensesSearchParameters;

public class ExpensesSearch {
    private volatile static ExpensesSearch instance;
    
    private ExpensesSearch() {
    	
    }
 
    /**
     * 
     * @return - a singleton instance of this object
     */ 
    public static ExpensesSearch getInstance() {
        if (instance == null) {
            synchronized (ExpensesSearch.class) {
                if (instance == null) {
                    instance = new ExpensesSearch();
                }
            }
        }
        return instance;
    }
    
	/**
	 * Gets list of spent units with given criteria
	 *
	 * @param parameters - search parameters
	 *
	 * @return - list of expenses that have given criteria
	 */
	public List<PExpense> findExpenses(final ExpensesSearchParameters parameters) {
		return HibernateUtil.queryInTransaction(new Resultable<List<PExpense>>() {
			@Override
			public List<PExpense> getResult(Session session) {
				StringBuilder hql = new StringBuilder(
						"select e from PExpense e join e.expensePlanItem epi join epi.expensePlan ep where 1 = 1");
				Map<String, Object> params = new HashMap<String, Object>();

				if(parameters.getExpensePlan() != null) {
					hql.append(" and epi.expensePlan = :expensePlan");
					params.put("expensePlan", parameters.getExpensePlan());
				} else if (parameters.getCurrency() != null) {
					hql.append(" and ep.currency = :currency");
					params.put("currency", parameters.getCurrency());
				}

				if(parameters.getCategory() != null) {
					hql.append(" and epi.category = :category");
					params.put("category", parameters.getCategory());
				}

				if(parameters.getFromDate() != null && parameters.getToDate() != null) {
					Calendar calToDate = Calendar.getInstance();
					calToDate.setTime(parameters.getToDate());
					calToDate.set(Calendar.HOUR_OF_DAY, 23);
					calToDate.set(Calendar.MINUTE, 59);
					calToDate.set(Calendar.SECOND, 59);
					calToDate.set(Calendar.MILLISECOND, 999);

					Calendar calFromDate = Calendar.getInstance();
					calFromDate.setTime(parameters.getFromDate());
					calFromDate.set(Calendar.HOUR_OF_DAY, 0);
					calFromDate.set(Calendar.MINUTE, 0);
					calFromDate.set(Calendar.SECOND, 0);
					calFromDate.set(Calendar.MILLISECOND, 0);

					hql.append(" and e.lastChangeDate >= :fromDate and e.lastChangeDate <= :toDate");
					params.put("fromDate", calFromDate.getTime());
					params.put("toDate", calToDate.getTime());
				}

				if(parameters.getLogicalEnum() != null && parameters.getPrice() != null) {
					String operator = null;
					switch(parameters.getLogicalEnum()) {
						case LESS:
							operator = "<";
							break;
						case LESS_EQUAL:
							operator = "<=";
							break;
						case EQUAL:
							operator = "=";
							break;
						case MORE_EQUAL:
							operator = ">=";
							break;
						case MORE:
							operator = ">";
							break;
						default:
							System.out.println("Uknown logical operation: " + parameters.getLogicalEnum());
					}
					if (operator != null) {
						hql.append(" and e.summ ").append(operator).append(" :price");
						params.put("price", new BigDecimal(parameters.getPrice()));
					}
				}

				if(parameters.getComment() != null) {
					hql.append(" and lower(e.comment) like lower(:comment)");
					params.put("comment", "%" + parameters.getComment() + "%");
				}

				SelectionQuery<PExpense> query = session.createSelectionQuery(hql.toString(), PExpense.class);
				for (Map.Entry<String, Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
				return query.list();
			}
		});
	}
}
