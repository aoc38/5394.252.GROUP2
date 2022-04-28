package com.prj.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.prj.cms.entity.Course;
import com.prj.cms.entity.CourseAssignments;
import com.prj.cms.entity.ProfessorCourses;
import com.prj.cms.service.AssignmentService;
import com.prj.cms.service.CourseService;
import com.prj.cms.service.ProfessorServive;
import com.prj.cms.service.impl.UserDetailsImpl;

@Controller
public class ProfessorController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private ProfessorServive professorCourseService;

	@GetMapping("/listProfessorCourses")
	public String listStudentCourses(Model model) {

		model.addAttribute("professorCourses", courseService.getAllCourses());
		return "professorPage";
	}

	@GetMapping("/professorDashboard")
	public String dashboard(Model model) {
		model.addAttribute("professorCourses", courseService.getAllCourses());
		return "studentDashboardPage";
	}

	@PostMapping("/registerProfessorCourse")
	public String registerProfessorCourse(@ModelAttribute("course") Course course, BindingResult result) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		System.out.println("Course Details from htmlpage : " + course.getCourseName() + "ID : " + course.getId());

		System.out.println("STudent Details : " + userDetails.getID());
		System.out.println("STudent Details : " + userDetails.getUsername());
		System.out.println("STudent Details : " + userDetails.getType());

		ProfessorCourses obj = new ProfessorCourses(course.getId(), userDetails.getID());
		List<ProfessorCourses> existingData = professorCourseService.findAllCourseProfessorMappings();
		if (isRegistered(existingData, obj)) {
			result.rejectValue("courseName", null, "Duplicate request. Please register a new course.");
		} else {

			professorCourseService.saveProfessorCourse(obj);
		}
		// return the dashboard page after saving
		return null;
	}

	private boolean isRegistered(List<ProfessorCourses> existingData, ProfessorCourses objProfessorCourses) {
		if (existingData != null && !existingData.isEmpty() && objProfessorCourses != null) {
			for (ProfessorCourses pc : existingData) {
				if (pc.getCourseId() == objProfessorCourses.getCourseId()
						&& pc.getProfessorId() == objProfessorCourses.getProfessorId()) {
					return true;
				}
			}
		}
		return false;
	}

	@PostMapping("/addAssignmentForCourse")
	public String addAssignment(@ModelAttribute("assignment") CourseAssignments assign, BindingResult result) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		System.out.println("STudent Details : " + userDetails.getID());
		System.out.println("STudent Details : " + userDetails.getUsername());
		System.out.println("STudent Details : " + userDetails.getType());

		return null;
	}

}
