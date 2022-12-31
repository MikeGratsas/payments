package lt.luminor.payments.form;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ClientModel {
    private Long id;

    @NotBlank(message = "{user.name.required}")
    private String name;
    @NotBlank(message = "{user.username.required}")
    @Size(min = 1, max = 32, message = "{user.username.size}")
    @Pattern(regexp="^[a-zA-Z0-9.]*$", message = "{user.username.chars}")
    private String username;
    @Email
    @Size(min = 1, max = 320, message = "{user.email.size}")
    private String email;
    @Size(max = 32, message = "{user.phone.size}")
    private String phone;
    @Size(min = 2, max = 2, message = "{user.language.size}")
    private String language;

    private boolean enabled = true;
    private boolean locked;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime created;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdated;
    
	public ClientModel() {
	}

    public ClientModel(Long id, String name, String username, String email, String phone, String language, boolean enabled, boolean locked, LocalDateTime created, LocalDateTime lastUpdated) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.enabled = enabled;
        this.locked = locked;
        this.created = created;
        this.lastUpdated = lastUpdated;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
