package bitcamp.java93.service;

import bitcamp.java93.domain.Member;

public interface MemberService {
  void add(Member member) throws Exception;
  Member getByEmailPassword(String email, String password) throws Exception;
  Member getProfile(Member member) throws Exception;
  Member getProfile2(Member member) throws Exception;
  void updatePwd(Member member) throws Exception;
  void updatePhoto(int no, String photoPath) throws Exception;
  void remove(int no) throws Exception;
}







