package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Project2Application {

	static List<Student>students = new ArrayList<>();

	public static void main(String[] args) {

		//Put code here for step 6
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/demo/student.txt"))) {
			String header;
			while ((header = br.readLine()) != null) {
				Student myStudent = new Student();
				String[] values = header.split(",");

				if(!values[0].equals("id")) {
					myStudent.setId(Integer.parseInt(values[0]));
					myStudent.setFirst_name(values[1]);
					myStudent.setGpa(Double.parseDouble(values[2]));
					myStudent.setEmail(values[3]);
					myStudent.setGender(values[4]);
					students.add(myStudent);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		SpringApplication.run(Project2Application.class, args);
	}

	@RestController
	public class testController {

		@GetMapping(value="/gpa")
		public double gpaCalc() {

			double totalGPA = 0;

			for (int i = 0; i < students.size(); i++) {
				totalGPA += students.get(i).getGpa();
			}

			double averageGPA = totalGPA / students.size();
			return averageGPA;
		}

		@GetMapping(value="/name/{first_name}")
		@ResponseBody
		public String search(@PathVariable String first_name) {
			int testl = -1;

			for (int i = 0; i < students.size(); i++) {
				if (first_name.equals(students.get(i).getFirst_name())){
					testl = i;
				}
			}
			if(testl == -1){
				return "Student not found.";
			} else {
				return students.get(testl).getId()
						+ "\n"
						+ students.get(testl).getFirst_name()
						+ "\n"
						+ students.get(testl).getGpa()
						+ "\n"
						+ students.get(testl).getEmail()
						+ "\n"
						+ students.get(testl).getGender();
			}
		}

		@GetMapping("/student")
		public String multiParam(
				@RequestParam String gpa,
				@RequestParam String gender) {

			int testl = -1;

			for (int i = 0; i < students.size(); i++) {
				if (Double.valueOf(gpa) == students.get(i).getGpa() && gender.equals(students.get(i).getGender())){
					testl = i;
				}
			}
			if(testl == -1){
				return "Student not found.";
			} else {
				return students.get(testl).getId()
						+ "\n"
						+ students.get(testl).getFirst_name()
						+ "\n"
						+ students.get(testl).getGpa()
						+ "\n"
						+ students.get(testl).getEmail()
						+ "\n"
						+ students.get(testl).getGender();
			}

		}



	}

}
