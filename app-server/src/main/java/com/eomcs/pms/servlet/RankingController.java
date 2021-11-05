package com.eomcs.pms.servlet;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.dao.ProductDao;
import com.eomcs.pms.domain.Product;


@WebServlet("/product/ranking")
public class RankingController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  ProductDao productDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    productDao = (ProductDao) 웹애플리케이션공용저장소.getAttribute("productDao");
  }


  @Override
  public void service (HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      System.out.println("1");
      Collection<Product> productList = productDao.ranking();
      System.out.println("2");
      request.setAttribute("productList", productList);
      System.out.println("3");
      request.getRequestDispatcher("Ranking.jsp").forward(request, response);

    }  catch (Exception e) {
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);
    }
  }
}




