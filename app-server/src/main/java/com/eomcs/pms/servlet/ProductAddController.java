package com.eomcs.pms.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.ProductDao;
import com.eomcs.pms.domain.Product;
import com.eomcs.pms.domain.ProductType;

@WebServlet("/product/add")
public class ProductAddController extends HttpServlet {
  private static final long serialVersionUID = 1L;

  SqlSession sqlSession;
  ProductDao productDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    ServletContext 웹애플리케이션공용저장소 = config.getServletContext();
    sqlSession = (SqlSession) 웹애플리케이션공용저장소.getAttribute("sqlSession");
    productDao = (ProductDao) 웹애플리케이션공용저장소.getAttribute("productDao");
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    //상품명
    //주종-상세주종
    //원산지
    //품종
    //용량
    //도수
    //당도(1-5)
    //산도(1-5)
    //바디감    

    try {
      Product product = new Product();
      product.setProductName(request.getParameter("productName"));
      String subType = request.getParameter("subType");
      String type = request.getParameter("type");

      ProductType productType = new ProductType();
      productType.setType(type);
      productType.setSubType(subType);

      product.setProductType(new ProductHandlerHelper(productDao).promptType(type, subType));

      product.setCountryOrigin(request.getParameter("countryOrigin"));

      if(product.getProductType().getType().equals("와인")) {
        product.setVariety(request.getParameter("variety"));
      }

      product.setVolume(Integer.parseInt(request.getParameter("volume")));
      product.setAlcoholLevel(Float.parseFloat(request.getParameter("alcoholLevel")));
      product.setSugarLevel(Integer.parseInt(request.getParameter("sugarLevel")));
      product.setAcidity(Integer.parseInt(request.getParameter("acidity")));
      product.setWeight(Integer.parseInt(request.getParameter("weight")));

      productDao.insert(product);
      sqlSession.commit();
      response.setHeader("Refresh", "1;url=list");
      request.getRequestDispatcher("ProductAdd.jsp").forward(request, response);
    } catch(Exception e){
      request.setAttribute("error", e);
      request.getRequestDispatcher("/Error.jsp").forward(request, response);   
    }
  }
}










