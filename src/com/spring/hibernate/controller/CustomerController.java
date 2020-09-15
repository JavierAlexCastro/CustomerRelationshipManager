package com.spring.hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.hibernate.entity.Customer;
import com.spring.hibernate.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//inject CustomerService
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		
		//get customers from Service
		List<Customer> customers = customerService.getCustomers();
		
		//add customers to the model
		model.addAttribute("customers", customers);
		
		//return jsp
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		
		//create model attribute to bind form data
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
		//return jsp
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer")Customer customer) {
		
		//use service to save customer
		customerService.saveCustomer(customer);
		
		//return jsp
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int customerId, Model model) {
		//get customer from the db
		Customer customer = customerService.getCustomer(customerId);
		
		//set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		
		//send over to form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") int customerId) {
		//invoke service for deleting
		customerService.deleteCustomer(customerId);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomer(@RequestParam("searchName")String searchName, Model model) {
		//search customers using service
		List<Customer> customers = customerService.searchCustomers(searchName);
		
		//debugging
		System.out.println("Retrieved customers--");
		for(Customer customer: customers) {
			System.out.println(customer);
		}
		
		//set customers as a model attribute to display search results
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	

}
