package com.example.test.student;





import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
	private String id;
	private String name;
	private float price;
	private String brand;
	private boolean sold;
}
