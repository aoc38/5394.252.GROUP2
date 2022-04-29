package com.prj.cms.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.prj.cms.entity.Course;
import com.prj.cms.entity.CourseAssignments;
import com.prj.cms.entity.ProfessorCourses;
import com.prj.cms.service.CourseAssignmentService;
import com.prj.cms.service.CourseService;
import com.prj.cms.service.ProfessorServive;
import com.prj.cms.service.impl.UserDetailsImpl;

@Controller
public class ProfessorController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private ProfessorServive professorCourseService;
	
	@Autowired
	private CourseAssignmentService assignmentService;

	@GetMapping("/listProfessorCourses")
	public String listProfessorCourses(Model model) {
		model.addAttribute("professorCourses", courseService.getAllCourses());
		return "professorPage";
	}
	
	@GetMapping("/assignmentDashboard")
	public String loadAssignments(Model model) {
		

		List<CourseAssignments> courseAssignments = assignmentService.getAllAssignments();
		List<Course> assignmentsRegistered = 
				
				courseService.getAllCourses().stream()
				.filter(f -> professorCoursesMapping.stream().anyMatch(s -> f.getId() == s.getCourseId()))
				.collect(Collectors.toList());
		coursesRegistered.stream().forEach(s -> System.out.println(s.toString()));
		model.addAttribute("professorCourseMappings", coursesRegistered);
		return "professorDashboardPage";
	
		
		model.addAttribute("assignmentslist",assignmentService.getAllAssignments() );
		return "AssignmentsHomePage";
	}

	@GetMapping("/professorDashboard")
	public String professorDashboard(Model model) {
		List<ProfessorCourses> professorCoursesMapping = professorCourseService.findAllCourseProfessorMappings();
		List<Course> coursesRegistered = courseService.getAllCourses().stream()
				.filter(f -> professorCoursesMapping.stream().anyMatch(s -> f.getId() == s.getCourseId()))
				.collect(Collectors.toList());
		coursesRegistered.stream().forEach(s -> System.out.println(s.toString()));
		model.addAttribute("professorCourseMappings", coursesRegistered);
		return "professorDashboardPage";
	}

	@PostMapping("/registerProfessorCourse")
	public String registerProfessorCourse(@ModelAttribute("course") Course course, BindingResult result) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		ProfessorCourses obj = new ProfessorCourses(course.getId(), userDetails.getID());
		List<ProfessorCourses> existingData = professorCourseService.findAllCourseProfessorMappings();
		if (isRegistered(existingData, obj)) {
			result.rejectValue("courseName", null, "Duplicate request. Please register a new course.");
		} else {

			professorCourseService.saveProfessorCourse(obj);
		}
		return "professorDashboardPage";
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
	public String addAssignment(@ModelAttribute("assignment") CourseAssignments assignments, BindingResult result) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		System.out.println("User Details : " + userDetails.getID());
		System.out.println("User Details : " + userDetails.getUsername());
		System.out.println("User Details : " + userDetails.getType());

		return "AssignmentsHomePage";
	}

	@GetMapping("/professorCourses/add/assignments/{id}")
	public String addCourseAssignments(@PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		System.out.println("User Details : " + userDetails.getID());
		System.out.println("User Details : " + userDetails.getUsername());
		System.out.println("User Details : " + userDetails.getType());

		return "AssignmentsHomePage";
	}

	@GetMapping("/professorCourses/{id}")
	public String deleteProfessorCourseMapping(@PathVariable int id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ProfessorCourses professorCourse = new ProfessorCourses(id, userDetails.getID());
		professorCourseService.deleteProfessorCourseMapping(professorCourse);

		return "redirect:/professorDashboard";
	}

}
