package bitcamp.java93.control.json;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import bitcamp.java93.domain.Member;
import bitcamp.java93.service.MemberService;

@RestController
@RequestMapping("/auth/")
@SessionAttributes({"loginMember"})
public class AuthControl {
  @Autowired
  MemberService memberService;

  @RequestMapping(path="login", method=RequestMethod.POST)
  public JsonResult login(String email, String password, Model model) throws Exception {

    Member member = null;
      member = memberService.getByEmailPassword(email, password);
    
    if (member != null) { 
      model.addAttribute("loginMember", member);
      
      return new JsonResult(JsonResult.SUCCESS, "ok");
      
    } else {
      return new JsonResult(JsonResult.FAIL, "fail");
    }
  }
  
  @RequestMapping("logout")
  public JsonResult logout(HttpSession session, SessionStatus status) throws Exception {
    status.setComplete();
    session.invalidate();  
    return new JsonResult(JsonResult.SUCCESS, "ok");
  }
  
  @RequestMapping("userinfo")
  public JsonResult userinfo(HttpSession session) throws Exception {
    Member loginMember = (Member)session.getAttribute("loginMember");
    return new JsonResult(JsonResult.SUCCESS, loginMember);
  }
}









