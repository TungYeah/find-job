package vn.minhtung.findJob.domain.dto;

import lombok.Getter;
import lombok.Setter;
import vn.minhtung.findJob.util.constant.GenderEnum;

import java.time.Instant;

@Getter
@Setter
public class UpdateUserDTO {

    private long id;

    private String name;

    private int age;

    private GenderEnum gender;

    private String address;

    private Instant updateAt;
}
