package com.example.demo.dto;

import lombok.*;
import org.hibernate.annotations.SecondaryRow;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequest {
    private String otp;
    private String email;
}
