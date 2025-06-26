package vn.minhtung.findJob.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import vn.minhtung.findJob.util.SecutiryUtil;
import vn.minhtung.findJob.util.constant.GenderEnum;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "ko duoc de trong")
    private String name;

    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "email ko dc de trong")
    private String email;

    @NotBlank(message = "ko dc de trong")
    private String password;

   private int age;

   @Enumerated(EnumType.STRING)
   private GenderEnum gender;

   private String address;

   @Column(columnDefinition = "MEDIUMTEXT")
   private String refreshToken;

   private Instant createAt;

   private Instant updateAt;

   private String createBy;

   private String updateBy;

   @PrePersist
   public void handleBeforeCreate(){
       this.createBy =  SecutiryUtil.getCurrentUserLogin().isPresent() == true ?
               SecutiryUtil.getCurrentUserLogin().get() : "" ;
       this.createAt = Instant.now();
   }

    @PreUpdate
    public void handleBeforeUpdate(){
        this.updateBy = SecutiryUtil.getCurrentUserLogin()
                .orElse("");

        this.updateAt = Instant.now();
    }
}
