package com.sopra.agile.cardio.common.model;

public class Sprint extends Base {

	private String name;
	private String startDate;
	private String endDate;
	private String goal;
	private int commitment;
	private int velocity;

	public Sprint() {
		super();
	}

	public Sprint(final String id, final String name, final String startDate, String endDate) {
		super(id);
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getCommitment() {
		return commitment;
	}

	public void setCommitment(int commitment) {
		this.commitment = commitment;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}
}
