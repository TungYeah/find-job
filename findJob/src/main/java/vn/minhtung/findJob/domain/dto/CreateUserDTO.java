package vn.minhtung.findJob.domain.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import vn.minhtung.findJob.util.constant.GenderEnum;

import java.time.Instant;
@Getter
@Setter
public class CreateUserDTO {

    private long id;

    private String name;

    private String email;

    private int age;

    private GenderEnum gender;

    private String address;

    private Instant createAt;

}
