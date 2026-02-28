package com.ecommerce.model.entity;

import com.ecommerce.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    
    @NotBlank
    @Column(unique = true, length = 50)
    private String username;
    
    @Email
    @NotBlank
    @Column(unique = true, length = 100)
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    private String phone;
    
    private String address;
    
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.CUSTOMER;
}
