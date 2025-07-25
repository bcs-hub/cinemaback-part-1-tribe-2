package ee.bcs.cinemaback.service.user.dto;

import ee.bcs.cinemaback.persistence.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RegistrationRequest implements Serializable {
    @Size(min = 1, max = 255)
    @NotNull
    private String username;
    @Size(max = 255)
    @NotNull
    @Size(min = 8, message = "Parool peab olema vähemalt 8 tähemärki")
    private String password;
    @Size(max = 255)
    @NotNull
    @Email(message = "Email peab sisaldama @ sümbolit, ning domeeni")
    private String email;


}
