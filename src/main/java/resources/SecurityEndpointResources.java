package resources;

public class SecurityEndpointResources {

	static final String HOST_STRING = "/qa";
	static final String SECURITY_STRING = "/security/";

	public String ServerStatus() {
		String serverStatuString = HOST_STRING + SECURITY_STRING + "status/info";

		return serverStatuString;

	}

	public String AdminLogin() {
		String adminLoginString = SECURITY_STRING + "admin/login";

		return adminLoginString;
	}

	public String AdminForgotPassword() {
		String adminForgotPasswordString = SECURITY_STRING + "admin/forgot-password";

		return adminForgotPasswordString;
	}

}
