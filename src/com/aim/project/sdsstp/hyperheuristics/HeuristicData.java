package com.aim.project.sdsstp.hyperheuristics;

/**
 * Data stored for SimpleChoiceFunction
 * @author Lekang Jiang
 *
 */
public class HeuristicData {

	private long timeLastApplied;
	
	private long previousApplicationDuration;
	
	private double f_delta;
	
	public HeuristicData() {
		
		this.timeLastApplied = 0;
		this.f_delta = 0;
		this.previousApplicationDuration = 1;
	}

	public long getTimeLastApplied() {
		return timeLastApplied;
	}

	public void setTimeLastApplied(long timeLastApplied) {
		this.timeLastApplied = timeLastApplied;
	}

	public double getF_delta() {
		return f_delta;
	}

	public void setF_delta(double f_delta) {
		this.f_delta = f_delta;
	}

	public long getPreviousApplicationDuration() {
		return previousApplicationDuration;
	}

	public void setPreviousApplicationDuration(long previousApplicationDuration) {
		this.previousApplicationDuration = previousApplicationDuration;
	}
}
