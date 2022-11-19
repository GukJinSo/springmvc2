package hello.login.domain.login;


import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String id, String password){
        return memberRepository.findByLoginId(id)
                .filter(member -> member.getPassword().equals(password))
                .orElse(null);
    }
}
