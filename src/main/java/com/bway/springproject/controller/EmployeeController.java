package com.bway.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bway.springproject.model.Employee;
import com.bway.springproject.service.DepartmentService;
import com.bway.springproject.service.EmployeeService;
import com.bway.springproject.utils.EmployeeExcelView;
import com.bway.springproject.utils.EmployeePdfView;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	@Autowired
	private DepartmentService deptService;
	
	@GetMapping("/employee")
	public String getEmployee(Model model) {
		
		model.addAttribute("dList",deptService.getAllDepts());
		
		return"EmployeeForm";
	}
	@PostMapping("/employee")
	public String postEmployee(@ModelAttribute Employee emp) {
		
		empService.addEmployee(emp);
		return"EmployeeForm";
	}
	
	@GetMapping("/employeelist")
	public String getEmps(Model model) {
		
		model.addAttribute("eList",empService.getAllEmployees());
		
		return"EmployeeListForm";
	}
	
	@GetMapping("/emp/delete")
	public String delete(@RequestParam Long id) {
		
		empService.deleteEmployee(id);
		
		return"redirect:/employeelist";
	}
	@GetMapping("/emp/edit")
	public String edit(@RequestParam Long id,Model model) {
		
		model.addAttribute("empObject",empService.getEmployeebyId(id));
		
		return"EmployeeEditForm";
	}
	@PostMapping("/emp/update")
	public String update(@ModelAttribute Employee emp) {
		
		empService.updateEmployee(emp);
		
		return"redirect:/employeelist";
	}
	@GetMapping("/emp/excel")
	public ModelAndView excel() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("eList",empService.getAllEmployees());
		mv.setView(new EmployeeExcelView());
		
		return mv;
	}
	@GetMapping("/emp/pdf")
	public ModelAndView pdf() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("eList",empService.getAllEmployees());
		mv.setView(new EmployeePdfView());
		return mv;
	}
}
