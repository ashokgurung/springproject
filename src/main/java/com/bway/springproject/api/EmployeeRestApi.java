package com.bway.springproject.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bway.springproject.model.Employee;
import com.bway.springproject.model.Product;
import com.bway.springproject.repository.ProductRepository;
import com.bway.springproject.service.EmployeeService;

@RestController
public class EmployeeRestApi {
	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private ProductRepository prodRepo;
	
    @GetMapping("/api/emp/list")
	public List<Employee> getAll() {
		
		return empService.getAllEmployees();
	}
    @GetMapping("/api/emp/{id}")
    public Employee getOne(@PathVariable Long id) {
    	
    	return empService.getEmployeebyId(id);
    }
    @PostMapping("/api/emp/add")
    public String add(@RequestBody Employee emp) {
    	
    	empService.addEmployee(emp);
    	
    	return"success";
    }
    @DeleteMapping("/api/emp/delete/{id}")
    public String delete(@PathVariable Long id) {
    	
    	empService.deleteEmployee(id);
    	
    	return"success";
    }
    @PutMapping("/api/emp/update")
    public String update(@RequestBody Employee emp) {
    	
    	empService.updateEmployee(emp);
    	
    	return"success";
    }
    @GetMapping("/api/emp/j2o")
    public String jsonToObject() {
    	
    	RestTemplate temp = new RestTemplate();
    	Employee e = temp.getForObject("http://localhost:8080/api/emp/9", Employee.class);
    	
    	return"FirstName = "+e.getFname();
    }
    @GetMapping("/api/emp/ja2oa")
    public String jsonArayToObjectArray() {
    	
    	RestTemplate rt = new RestTemplate();
    	Employee[] emps = rt.getForObject("http://localhost:8080/api/emp/list", Employee[].class);
    	
    	return"FirstName = "+emps[0].getFname();
    }
    @GetMapping("/api/emp/loadProduct")
    public String loadProducts() {
    	
    	RestTemplate rt = new RestTemplate();
    	Product[] products = rt.getForObject("https://fakestoreapi.com/products", Product[].class);
    	
    	prodRepo.saveAll(Arrays.asList(products));
    	
    	return"success";
    }
}
