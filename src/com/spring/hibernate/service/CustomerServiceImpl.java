package com.spring.hibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.hibernate.dao.CustomerDAO;
import com.spring.hibernate.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	//inject customerDAO
	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		//delegate work to DAO
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		//delegate work to DAO
		customerDAO.saveCustomer(customer);	
	}

	@Override
	@Transactional
	public Customer getCustomer(int customerId) {
		//delegate work to DAO
		return customerDAO.getCustomer(customerId);
	}

	@Override
	@Transactional
	public void deleteCustomer(int customerId) {
		//delegate work to DAO
		customerDAO.deleteCustomer(customerId);
		
	}

	@Override
	@Transactional
	public List<Customer> searchCustomers(String searchName) {
		return customerDAO.searchCustomers(searchName);
	}

}
