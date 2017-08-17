package bitcamp.java93.control.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bitcamp.java93.domain.Event;
import bitcamp.java93.domain.Member;
import bitcamp.java93.domain.Musician;
import bitcamp.java93.service.EventService;

@RestController
@RequestMapping("/event/")
public class EventControl {
  
  @Autowired ServletContext servletContext;
  @Autowired EventService eventService;
  
  
  @RequestMapping("addReherse")
  public JsonResult addReherse(Event event, String eventRegistTheme, String eventRegistMajor, String eventRegistGenre, HttpSession session) throws Exception {
    String[] themeList = eventRegistTheme.split(",");
    String[] majorList = eventRegistMajor.split(",");
    String[] genreList = eventRegistGenre.split(",");
    
    ArrayList<String> registThemeList = new ArrayList<>();
    ArrayList<String> registMajorList = new ArrayList<>();
    ArrayList<String> registGenreList = new ArrayList<>();
    
    for (String theme : themeList) {
      registThemeList.add(theme);
    } 
    
    for (String major : majorList) {
      registMajorList.add(major);
    } 
    
    for (String genre : genreList) {
      registGenreList.add(genre);
    } 
    
    event.setEventRegistTheme(registThemeList);
    event.setEventRegistMajor(registMajorList);
    event.setEventRegistGenre(registGenreList);
    event.setWriter(getLoginMember(session).getNo());
    eventService.add(event);
    eventService.RegistEventCategory(event);
    eventService.RegistEventReherse(event);
    return new JsonResult(JsonResult.SUCCESS, "ok");
  }
  
  @RequestMapping("add")
  public JsonResult add(Event event, String eventRegistTheme, String eventRegistMajor, String eventRegistGenre, HttpSession session) throws Exception {
    String[] themeList = eventRegistTheme.split(",");
    String[] majorList = eventRegistMajor.split(",");
    String[] genreList = eventRegistGenre.split(",");
    
    ArrayList<String> registThemeList = new ArrayList<>();
    ArrayList<String> registMajorList = new ArrayList<>();
    ArrayList<String> registGenreList = new ArrayList<>();
    
    for (String theme : themeList) {
      registThemeList.add(theme);
    } 
    
    for (String major : majorList) {
      registMajorList.add(major);
    } 
    
    for (String genre : genreList) {
      registGenreList.add(genre);
    } 
    
    event.setEventRegistTheme(registThemeList);
    event.setEventRegistMajor(registMajorList);
    event.setEventRegistGenre(registGenreList);
    
    event.setWriter(getLoginMember(session).getNo());
    eventService.add(event);
    
    eventService.RegistEventCategory(event);
    return new JsonResult(JsonResult.SUCCESS, "ok");
  }
  
  
  
  /* musimode 나에게 꼭 맞는 이벤트*/
  @RequestMapping("listRecommand")
  public JsonResult listRecommand(HttpSession session) {
    JsonResult result = new JsonResult();
    try {
      HashMap<String,Object> dataMap = new HashMap<>();
      dataMap.put("listRecommand",eventService.listRecommand(getLoginMember(session).getNo()));

      result.setData(dataMap);
      result.setStatus(JsonResult.SUCCESS);
    } catch (Exception e) {
      result.setStatus(JsonResult.ERROR);
      e.printStackTrace();
    }
    return result;
  }
  
  /* musimode 최근 이벤트*/
  @RequestMapping("listRecent")
  public JsonResult listRecent(HttpSession session) {
    JsonResult result = new JsonResult();
    try {
      HashMap<String,Object> dataMap = new HashMap<>();
      dataMap.put("listRecent",eventService.listRecent(getLoginMember(session).getNo()));

      result.setData(dataMap);
      result.setStatus(JsonResult.SUCCESS);
    } catch (Exception e) {
      result.setStatus(JsonResult.ERROR);
      e.printStackTrace();
    }
    return result;
  }
  
  @RequestMapping("checkEvent")
  public JsonResult checkEvent(HttpSession session, int no){
    JsonResult result = new JsonResult();
      try {
        List<Event> eventList = eventService.checkEvent(getLoginMember(session).getNo(), no);
        
        if (eventList != null) {
          result.setStatus(JsonResult.SUCCESS);

          HashMap<String,Object> dataMap = new HashMap<>();
          dataMap.put("eventList", eventList);

          result.setData(dataMap);
        } else { 
          return new JsonResult(JsonResult.SUCCESS, "noEvent");
        }
        
      } catch (Exception e) {
        result.setStatus(JsonResult.ERROR);
      }
    return result;
  }

  @RequestMapping("prEvent")
  public JsonResult prEvent(int muNo, int eNo){

    JsonResult result = new JsonResult();
      try {
        eventService.prEvent(muNo, eNo);
        
        return new JsonResult(JsonResult.SUCCESS, "success");
        
      } catch (Exception e) {
        result.setStatus(JsonResult.ERROR);
      }
    return result;
  }
  
  @RequestMapping("listFavor")
  public JsonResult listFavor(HttpSession session) throws Exception {
    JsonResult result = new JsonResult();

    try {
      List<Event> listFavor = (List<Event>) eventService.listFavor(getLoginMember(session).getNo());
      HashMap<String,Object> dataMap = new HashMap<>();
      dataMap.put("listFavor", listFavor);
      result.setData(dataMap);
    } catch (Exception e) {
      result.setStatus(JsonResult.ERROR);
      result.setData(e.getMessage());
    }
    return result;
  }

  @RequestMapping("favorRemove")
  public JsonResult favorRemove(HttpSession session, int no) throws Exception {
    eventService.favorRemove(getLoginMember(session).getNo(), no);
    return new JsonResult(JsonResult.SUCCESS, "ok");
  }

  @RequestMapping("favorAdd")
  public JsonResult favorAdd(HttpSession session, int no) throws Exception {
    eventService.favorAdd(getLoginMember(session).getNo(), no);
    return new JsonResult(JsonResult.SUCCESS, "ok");
  }
  
  @RequestMapping("listSurf")
  public JsonResult listSurf(HttpSession session) throws Exception {
    JsonResult result = new JsonResult();

      try {
        List<Event> listSurf = (List<Event>) eventService.listSurf(getLoginMember(session).getNo());

        result.setStatus(JsonResult.SUCCESS);

        HashMap<String,Object> dataMap = new HashMap<>();
        dataMap.put("listSurf", listSurf);
        result.setData(dataMap);

      } catch (Exception e) {
        result.setStatus(JsonResult.ERROR);
      }
    return result;
  }
  

  @RequestMapping("listSearchResult")
  public JsonResult listSearchResult(HttpSession session, String search) {
    JsonResult result = new JsonResult();

    try {
      List<Event> eventList = eventService.listSearchResult(search);

      result.setStatus(JsonResult.SUCCESS);

      HashMap<String,Object> dataMap = new HashMap<>();
      dataMap.put("listSearchResult", eventList);
      result.setData(dataMap);

    } catch (Exception e) {
      e.printStackTrace();
      result.setStatus(JsonResult.ERROR);
    }
  return result;
}

  /*나의이벤트 > 모집중 이벤트 리스트*/
  @RequestMapping("listRecruiting")
  public JsonResult listRecruiting(HttpSession session) {
    JsonResult result = new JsonResult();
    try {
      HashMap<String,Object> dataMap = new HashMap<>();
      dataMap.put("listRecruiting",eventService.listRecruiting(getLoginMember(session).getNo()));

      result.setData(dataMap);
      result.setStatus(JsonResult.SUCCESS);
    } catch (Exception e) {
      result.setStatus(JsonResult.ERROR);
      e.printStackTrace();
    }
    return result;
  }
  
  /*나의이벤트 > 진행중 이벤트 리스트*/
  @RequestMapping("listOngoing")
  public JsonResult listOngoing(HttpSession session) {
    JsonResult result = new JsonResult();
    try {
      HashMap<String,Object> dataMap = new HashMap<>();
      dataMap.put("listOngoing",eventService.listOngoing(getLoginMember(session).getNo()));

      result.setData(dataMap);
      result.setStatus(JsonResult.SUCCESS);
    } catch (Exception e) {
      result.setStatus(JsonResult.ERROR);
      e.printStackTrace();
    }
    return result;
  }
  
  private Member getLoginMember(HttpSession session) {
    Member loginMember = (Member) session.getAttribute("loginMember");
      return loginMember;
  }
  
}









