package com.manish.javadev.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.manish.javadev.dao.CustomerDAO;
import com.manish.javadev.model.Customer;
import com.manish.javadev.to.CustomerTO;

@SuppressWarnings({"unchecked","rawtypes","unused"})
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDAO {

	// Add Customer
	public void addCustomer(final CustomerTO cto) {
		
		Customer cust = (Customer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Customer doInHibernate(Session session)
							throws HibernateException {
						Customer cust = new Customer(cto.getFirstName(), cto
								.getLastName(), cto.getAge(), cto.getCity());

						session.saveOrUpdate(cust);
						return cust;
					}
				});

	}

	// Update Customer By Id
	public void updateCustomer(int custId) {
		Customer cust = getHibernateTemplate().load(Customer.class, custId);
		cust.setFirstName("Updated FirstName");
		getHibernateTemplate().update(cust);
	}

	// Delete Customer By Id
	public void deleteCustomer(int custId) {
		Customer cust = getHibernateTemplate().load(Customer.class, custId);
		getHibernateTemplate().delete(cust);
	}

	// Get All Customer
	public List<CustomerTO> getAllCustomer() {
		List<CustomerTO> custTo = new ArrayList<CustomerTO>();
		String sql = "from Customer c";
		List<Customer> list = getHibernateTemplate().find(sql);
		for (Customer cust : list) {
			CustomerTO custto = new CustomerTO(cust.getCustId(),
					cust.getFirstName(), cust.getLastName(), cust.getAge(),
					cust.getCity());
			custTo.add(custto);
		}
		return custTo;
	}

	// Get Customer By Id
	public CustomerTO getCustomerById(int custId) {
		Customer cust = getHibernateTemplate().load(Customer.class, custId);
		CustomerTO custTo = new CustomerTO(cust.getCustId(),
				cust.getFirstName(), cust.getLastName(), cust.getAge(),
				cust.getCity());
		return custTo;

	}
}
