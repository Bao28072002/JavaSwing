package model;

public class Login {
	private String username;
	private String password;
	private String jobRole;

	public Login() {

	}

	public Login(String username, String password, String jobRole) {
		this.username = username;
		this.password = password;
		this.jobRole = jobRole;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + ", jobRole=" + jobRole + "]";
	}

}
