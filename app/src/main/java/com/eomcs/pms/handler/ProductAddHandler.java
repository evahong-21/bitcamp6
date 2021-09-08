package com.eomcs.pms.handler;

import com.eomcs.menu.Menu;
import com.eomcs.pms.App;
import com.eomcs.pms.domain.Product;
import com.eomcs.util.Prompt;

public class ProductAddHandler extends AbstractProductHandler {
  int productNumber = 1;


  @Override
  public void execute() {
    if (App.getLoginUser().getAuthority() != Menu.ACCESS_SELLER &
        App.getLoginUser().getAuthority() != Menu.ACCESS_ADMIN) {
      System.out.println("해당 메뉴 접근 권한이 없습니다.");
      return;
    }

    System.out.println("[상품 등록]");

    Product product = new Product();

    product.setProductNumber(productNumber++);
    product.setProductName(Prompt.inputString("상품명 : "));
    product.setProductType(checkType("주종 : "));
    product.setCountryOrigin(Prompt.inputString("원산지 : "));
    product.setVariety(Prompt.inputString("품종 : "));
    product.setAlcoholLevel(Prompt.inputFloat("알콜도수 : ")); 
    product.setSugerLevel(checkNum("당도(1-5) : "));
    product.setAcidity(checkNum("산도(1-5) : "));
    product.setWeight(checkNum("바디감(1-5) : "));
    App.productList.add(product);
    System.out.println("상품을 등록하였습니다.");
  }

}












