package com.orangelabs.tuya2capi.tuya2cApi.business.orangeuser.resp;

public class UserResp {
	
	private String _id;
	
    private String firstname;
    
    private String lastname;
    
    private String email;
    
    private String password;
    
    private String role;
    
    private String country;
    
    private OrangUserType type;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public OrangUserType getType() {
		return type;
	}

	public void setType(OrangUserType type) {
		this.type = type;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
