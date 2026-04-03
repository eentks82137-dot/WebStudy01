package kr.or.ddit.auth;

import java.security.Principal;
import java.util.List;

import kr.or.ddit.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Authentication implements Principal {
    private final MemberDTO principal;
    private final List<String> authorities;

    @Override
    public String getName() {
        return principal.getMemId();
    }
}
