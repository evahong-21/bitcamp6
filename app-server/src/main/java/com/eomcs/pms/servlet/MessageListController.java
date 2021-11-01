package com.eomcs.pms.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import com.eomcs.pms.dao.MessageDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Message;
import com.eomcs.pms.domain.MessageList;

@WebServlet("/message/list")
public class MessageListController extends HttpServlet{

  private static final long serialVersionUID = 1L;

  MessageDao messageDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    messageDao = (MessageDao) 웹애플리케이션공용저장소.getAttribute("messageDao");
  }

  public static int messageNumber = 1;


  @Override
  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    try {
      Member member = new Member();
      member.setNumber(1);
      member.setId("admin");

      int nowLoginNo = member.getNumber();
      String nowLoginId = member.getId();

      //      if (ClientApp.getLoginUser().isMessageUpdate()) {
      //        //메시지 알림 보내기
      //        //        memberPrompt.returnMessageUpdate(nowLoginId);
      //      }


      Collection<Message> messages = messageDao.findAll(nowLoginNo);

      MessageList messageList = new MessageList(); 
      messageList.setId(nowLoginId);
      messageList.setMessage((List<Message>) messages);

      request.setAttribute("messages", messages);   
      request.getRequestDispatcher("MessageList.jsp").forward(request, response);

    }catch (Exception e) {
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}
