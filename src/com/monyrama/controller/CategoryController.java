package com.monyrama.controller;

import com.monyrama.db.enumarations.EntityStates;
import com.monyrama.entity.PCategory;
import com.monyrama.entity.PExpense;
import com.monyrama.ui.resources.Resources;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Petro_Verheles
 */
public class CategoryController extends AbstractController<PCategory> {

    private static final int MILLISECONDS_IN_DAY = 86400000;

    private CategoryController(Class<PCategory> clazz) {
        super(clazz);
    }

    private static CategoryController instance;


    public static CategoryController instance() {
        if (instance == null) {
            instance = new CategoryController(PCategory.class);
        }
        return instance;
    }

    public BigDecimal calculateAvarageExpenseSumPerDay(final PCategory category) {
        return HibernateUtil.queryInTransaction(new Resultable<BigDecimal>() {
            @Override
            public BigDecimal getResult(Session session) {
                String expense = PExpense.class.getSimpleName();

                Query minDateQuery = session.createQuery("SELECT MIN(expense.lastChangeDate) FROM " + expense + " expense WHERE " +
                        "expense.expensePlanItem.category = :category");
                minDateQuery.setParameter("category", category);
                Date minPastDate = (Date) minDateQuery.uniqueResult();

                if (minPastDate == null) {
                    return BigDecimal.ZERO;
                }

                int daysPeriod = 90;

                Date today = Calendar.getInstance().getTime();
                Calendar pastCalendar = Calendar.getInstance();
                pastCalendar.add(Calendar.DAY_OF_YEAR, -daysPeriod);
                Date fromDate = pastCalendar.getTime();

                if (fromDate.before(minPastDate)) {
                    fromDate = minPastDate;
                }

                daysPeriod = (int) ((today.getTime() / MILLISECONDS_IN_DAY) - (fromDate.getTime() / MILLISECONDS_IN_DAY)) + 1;

                Query avgSumQuery = session.createQuery("SELECT SUM(expense.summ) / " + daysPeriod + " FROM " + expense + " expense WHERE " +
                        "expense.expensePlanItem.category = :category " +
                        "AND expense.lastChangeDate >= :from AND expense.lastChangeDate <= :to");
                avgSumQuery.setParameter("category", category);
                avgSumQuery.setParameter("from", atStartOfDay(fromDate));
                avgSumQuery.setParameter("to", atEndOfDay(today));
                BigDecimal result = (BigDecimal) avgSumQuery.uniqueResult();
                if (result != null) {
                  result = result.setScale(0, RoundingMode.HALF_UP);
                }
                return result;
            }
        });
    }

    public Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public PCategory getDefaultCategory() {
        return HibernateUtil.queryInTransaction(new Resultable<PCategory>() {
            @Override
            public PCategory getResult(Session session) {
                Query query = session.createQuery("SELECT category FROM PCategory category WHERE category.isDefault = true");
                PCategory category = (PCategory)query.uniqueResult();
                if (category == null) {
                    category = new PCategory();
                    category.setState(EntityStates.ACTIVE.getCode());
                    category.setName(Resources.getString("category.default.name"));
                    category.setIsDefault(true);
                    instance().createOrUpdate(category);
                }
                return category;
            }
        });
    }
}
