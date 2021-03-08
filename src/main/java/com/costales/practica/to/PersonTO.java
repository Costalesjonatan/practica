package com.costales.practica.to;

import lombok.*;
import org.springframework.stereotype.Component;
import java.sql.Timestamp;

@Data
@Component
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PersonTO {
    private Long id;
    private Integer documentType;
    private String gender;
    private Integer bornDate;
    private String documentLastNumbers;
    private String lastName;
    private String birthDate;
    private String name;
    private String maritalStatus;
    private String preferredContact;
    private String indActive;
    private Long phoneId;
    private Long addressId;
    private Long emailId;
    private String naturalPerson;
    private Timestamp createDatetime;
    private String createUser;
    private Timestamp updateDatetime;
    private String updateUser;
    private Long typeOfJob;
    private String businessName;
    private String normalizationCode;
    private String documentNumber;
    private String cacCode;
    private Long agendaCode;
}
