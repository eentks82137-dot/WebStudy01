package kr.or.ddit.member.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자의 정보를 가진 domain layer
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO implements Serializable {
    private String memId;
    private String memPass;
    private String memName;
    private String memRegno1;
    private String memRegno2;
    private LocalDate memBir;
    private String memZip;
    private String memAdd1;
    private String memAdd2;
    private String memHometel;
    private String memComtel;
    private String memHp;
    private String memMail;
    private String memJob;
    private String memLike;
    private String memMemorial;
    private LocalDate memMemorialday;
    private Integer memMileage;
    private String memDelete;
    private List<String> memRoles;
}
