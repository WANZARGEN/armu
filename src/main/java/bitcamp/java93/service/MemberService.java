package bitcamp.java93.service;

import bitcamp.java93.domain.Member;

public interface MemberService {
  void add(Member member) throws Exception;
  Member getByEmailPassword(String email, String password) throws Exception;
}







