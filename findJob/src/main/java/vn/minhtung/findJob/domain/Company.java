package vn.minhtung.findJob.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import vn.minhtung.findJob.util.SecutiryUtil;

import java.time.Instant;

@Entity
@Table(name = "comapnies")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "ko dc de trong")
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String address;

    private String logo;

    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss a", timezone = "GMT + 7")
    private Instant createAt;

    private Instant updateAt;

    private String createBy;

    private String updateBy;

    @PrePersist
    public void handleBerforeCreate(){
        this.createBy = SecutiryUtil.getCurrentUserLogin().isPresent() == true ?
                SecutiryUtil.getCurrentUserLogin().get() : "" ;
        this.createAt = Instant.now();
    }

    @PreUpdate
    public void handleBerforeUpdate(){
        this.updateBy = SecutiryUtil.getCurrentUserLogin().isPresent() == true ?
                SecutiryUtil.getCurrentUserLogin().get() : "" ;
        this.updateAt = Instant.now();
    }

}
