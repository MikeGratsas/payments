package lt.luminor.payments.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationModel {

    @NotBlank(message = "{user.name.required}")
    private String name;
    @NotBlank(message = "{user.username.required}")
    @Size(min = 1, max = 32, message = "{user.username.size}")
    @Pattern(regexp="^[a-zA-Z0-9.]*$", message = "{user.username.chars}")
    private String username;
    @NotBlank(message = "{user.password.required}")
    @Size(min = 1, max = 60, message = "{user.password.size}")
    @Pattern(regexp="^[a-zA-Z0-9.\\-_]*$", message = "{user.password.chars}")
    private String password;
    @Email
    @Size(min = 1, max = 320, message = "{user.email.size}")
    private String email;
    @Size(max = 32, message = "{user.phone.size}")
    private String phone;
    @Size(min = 2, max = 2, message = "{user.language.size}")
    private String language;

    private boolean enabled;

    public RegistrationModel() {
    }

    public RegistrationModel(String name, String username, String password, String email, String phone, String language) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.language = language;
     }

    public RegistrationModel(String name, String username, String password, String email, String phone, String language, boolean enabled) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.enabled = enabled;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}