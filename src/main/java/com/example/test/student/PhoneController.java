package com.example.test.student;

import java.io.IOException;



import java.sql.SQLException;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class PhoneController {
	private  PhoneDAO studentDAO = new PhoneDAO();
	@GetMapping("/phones")
	public List<Phone> getstudents(Model model) throws IOException {
		
		List<Phone> students = studentDAO.selectAllstudents();
		model.addAttribute("students", students);
		return students;
	}

	@GetMapping("/phone/{id}")
	public String getstudent(Model model, @PathVariable String id) {
		model.addAttribute("id", id);
		Phone student = studentDAO.selectstudent(id);
		model.addAttribute("student", student);
		return "phone_detail";
	}

	@PostMapping("/phone/save")
	public String addstudent(Phone student,RedirectAttributes attributes ) throws SQLException,IOException{
		if(!studentDAO.check(student.getId())) {
			attributes.addFlashAttribute("err", true);
			return "redirect:/phone/-1";
		}
		studentDAO.insertstudent(student);
		return "redirect:/phones";
	}

	@PutMapping("/phone/save/{id}")
	public String updatestudent(Phone student, @PathVariable String id)throws IOException,SQLException {
		studentDAO.updatestudent(student);
		return "redirect:/phones"; 
	}
	
	@DeleteMapping("/phone/delete/{id}")
	public String deletestudent(Phone student,@PathVariable String id)throws IOException,SQLException {
		studentDAO.deletestudent(id);
		return "redirect:/phones";
	}

}
