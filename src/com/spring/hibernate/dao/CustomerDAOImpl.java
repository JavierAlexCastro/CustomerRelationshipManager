package com.spring.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.hibernate.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	//inject session factory
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//create query and sort by last name
		Query<Customer> query = currentSession.createQuery(" from Customer order by lastName ", Customer.class);
		
		//execute query and get result list
		List<Customer> customers = query.getResultList();
		
		//return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//save customer
		currentSession.saveOrUpdate(customer);
		
	}

	@Override
	public Customer getCustomer(int customerId) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//retrieve customer info from db
		Customer customer = currentSession.get(Customer.class, customerId);
		
		//return customer
		return customer;
	}

	@Override
	public void deleteCustomer(int customerId) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//delete customer from db
		Query<?> query = currentSession.createQuery(" delete from Customer where id=:customerId" );
		query.setParameter("customerId", customerId);
		query.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//define query variable
		Query<Customer> query = null;
		
		if(searchName != null && searchName.trim().length() > 0) {
			//search for customer in db based on searchName
			query = currentSession.createQuery(" from Customer where lower(firstName) like :searchName or lower(lastName) like :searchName ", Customer.class);
			query.setParameter("searchName", "%" + searchName.toLowerCase() + "%");
		}else {
			//search for all customers in db
			query = currentSession.createQuery(" from Customer ", Customer.class);
		}
		
		//retrieve results from db
		List<Customer> customers = query.getResultList();
		
		//return results
		return customers;
	}

}
