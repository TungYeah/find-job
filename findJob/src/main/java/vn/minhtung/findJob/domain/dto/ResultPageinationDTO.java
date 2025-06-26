package vn.minhtung.findJob.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPageinationDTO {
    private Meta meta;

    private Object result;
}
