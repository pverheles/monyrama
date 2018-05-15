package com.monyrama.db.search;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import com.monyrama.controller.HibernateUtil;
import com.monyrama.entity.PExpense;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

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
	public List<PExpense> findExpenses(ExpensesSearchParameters parameters) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
			
		//Criteria envelopeCriteria = session.createCriteria(PExpensePlanItem.class);

		Criteria expenseCriteria = session.createCriteria(PExpense.class);
		expenseCriteria = expenseCriteria.createAlias("expensePlanItem", "epi");
		expenseCriteria = expenseCriteria.createAlias("epi.expensePlan", "ep");

		if(parameters.getExpensePlan() != null) {
			expenseCriteria.add(Restrictions.eq("epi.expensePlan", (parameters.getExpensePlan())));
		} else if (parameters.getCurrency() != null) {
			expenseCriteria.add(Restrictions.eq("ep.currency", (parameters.getCurrency())));
		}
		
		if(parameters.getCategory() != null) {
			expenseCriteria.add(Restrictions.eq("epi.category", parameters.getCategory()));
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
			
			expenseCriteria.add(Restrictions.ge("lastChangeDate", calFromDate.getTime()));
			expenseCriteria.add(Restrictions.le("lastChangeDate", calToDate.getTime()));
		}

		if(parameters.getLogicalEnum() != null && parameters.getPrice() != null) {
			BigDecimal price = new BigDecimal(parameters.getPrice());
			switch(parameters.getLogicalEnum()) {
				case LESS:
					expenseCriteria.add(Restrictions.lt("summ", price));
					break;
				case LESS_EQUAL:
					expenseCriteria.add(Restrictions.le("summ", price));
					break;
				case EQUAL:
					expenseCriteria.add(Restrictions.eq("summ", price));
					break;
				case MORE_EQUAL:
					expenseCriteria.add(Restrictions.ge("summ", price));
					break;
				case MORE:
					expenseCriteria.add(Restrictions.gt("summ", price));
					break;
				default:
					System.out.println("Uknown logical operation: " + parameters.getLogicalEnum());
			}
		}

		if(parameters.getComment() != null) {
			expenseCriteria.add(Restrictions.ilike("comment", "%" + parameters.getComment() + "%"));
		}

		List<PExpense> resultList = expenseCriteria.list();
        session.getTransaction().commit();
        
        return resultList;
	}
}
