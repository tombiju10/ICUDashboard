package com.Nest.Icu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agecondition {

    
	private int age = 26;
    double maxHeartbeat = 85;
    double minHeartbeat = 62;
    double minSystolePressure = 100;
    double maxSystolePressure = 140;
    double minDiastolePressure = 62;
    double maxDiastolePressure = 98;
	
   
    public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getMaxHeartbeat() {
		return maxHeartbeat;
	}
	public double getMinHeartbeat() {
		return minHeartbeat;
	}
	public double getMinSystolePressure() {
		return minSystolePressure;
	}
	public double getMaxSystolePressure() {
		return maxSystolePressure;
	}
	public double getMinDiastolePressure() {
		return minDiastolePressure;
	}
	public double getMaxDiastolePressure() {
		return maxDiastolePressure;
	}
    
}
