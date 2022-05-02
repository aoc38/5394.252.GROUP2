package com.prj.cms;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prj.cms.entity.Course;
import com.prj.cms.repository.CourseRepository;
import com.prj.cms.service.impl.CourseServiceImpl;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
	@InjectMocks
	CourseServiceImpl service;
	@Mock
	CourseRepository repository;

	Course course = new Course();

	@Test
	void saveAssignmentTest() {
		when(repository.save(course)).thenReturn(course);
		service.saveCourse(course);
		verify(repository, atLeastOnce()).save(course);
	}

}
