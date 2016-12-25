package com.sopra.agile.cardio.common.model;

import org.joda.time.LocalDate;

public class SprintDay {

	private LocalDate day;
	private int done;

	public SprintDay() {
		// Empty constructor
	}

	public SprintDay(LocalDate day, int commitmentLeft) {
		this.day = day;
		this.done = commitmentLeft;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}
}
