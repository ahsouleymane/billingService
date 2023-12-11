package net.sancfis.billingService.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_DEFAULT)
public class User {
    private Long id;
    @NotEmpty(message = "Le prénom est obligatoire")
    private String firstName;
    @NotEmpty(message = "Le nom est obligatoire")
    private String lastName;
    @NotEmpty(message = "L'adresse mail est obligatoire")
    @Email(message = "Svp entrer une adresse mail valide")
    private String email;
    @NotEmpty(message = "Le mot de passe est obligatoire")
    private String password;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private Boolean enabled;
    private Boolean notLocked;
    private Boolean usingMfa;
    private LocalDateTime createdAt;

}
