package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.domain.Product;
import com.eomcs.util.Prompt;

public class ProductHandler {

  List<Product> alcoholList;

  //Manager loginPrivacy;
  public ProductHandler(List<Product> alcoholList) {
    this.alcoholList = alcoholList;
    //this.loginPrivacy = loginPrivacy;
  }

  public void add(int auth) {
    if (auth == 0 || auth == 1 ) {
      System.out.println("해당 메뉴는 판매자 권한입니다.");
      return;
    }

    System.out.println("[상품 등록]");

    Product product = new Product();

    product.setProductNumber(Prompt.inputInt("번호? "));
    product.setProductName(Prompt.inputString("상품명? "));
    product.setProductType(Prompt.inputString("주종? "));
    product.setCountryOrigin(Prompt.inputString("원산지? "));
    product.setVariety(Prompt.inputString("품종? "));
    product.setAlcoholLevel(Prompt.inputFloat("알콜도수? ")); 
    product.setSugerLevel(checkNum("당도(1-5)"));
    product.setAcidity(checkNum("산도(1-5)"));
    product.setWeight(checkNum("바디감(1-5)"));

    alcoholList.add(product);
  }

  public void list() {

    System.out.println("[상품 목록]");

    Product[] list = alcoholList.toArray(new Product[0]);

    for (Product alcohol : list) {
      System.out.printf("%d, %s, %s, %s, %s, %.2f, %d, %d, %d \n", 
          alcohol.getProductNumber(), 
          alcohol.getProductName(), 
          alcohol.getProductType(), 
          alcohol.getCountryOrigin(),
          alcohol.getVariety(),
          alcohol.getAlcoholLevel(),
          alcohol.getSugerLevel(),
          alcohol.getAcidity(),
          alcohol.getWeight());

    }
  }

  public void detail() {
    System.out.println("[상품 상세보기]");
    int no = Prompt.inputInt("번호? ");

    Product alcohol = findByNo(no);

    if (alcohol == null) {
      System.out.println("해당 번호의 상품이 없습니다.");
      return;
    }

    System.out.printf("상품이름: %s\n", alcohol.getProductName());
    System.out.printf("주종: %s\n", alcohol.getProductType());
    System.out.printf("원산지: %s\n", alcohol.getCountryOrigin());
    System.out.printf("품종: %s\n", alcohol.getVariety());
    System.out.printf("알콜도수: %.2f\n", alcohol.getAlcoholLevel());
    System.out.printf("당도: %d\n", alcohol.getSugerLevel());
    System.out.printf("산도: %d\n", alcohol.getAcidity());
    System.out.printf("바디감: %d\n", alcohol.getWeight());

  }

  public void update(int auth) {
    if (auth == 0 || auth == 1 ) {
      System.out.println("해당 메뉴는 판매자 권한입니다.");
      return;
    }
    while(true) {
      System.out.println("[상품 변경]");
      int no = Prompt.inputInt("번호? ");

      Product alcohol = findByNo(no);

      if (alcohol == null) {
        System.out.println("해당 번호의 상품이 없습니다.");
        return;
      }

      String name = Prompt.inputString("상품이름(" + alcohol.getProductName()  + ")? ");
      String kind = Prompt.inputString("주종(" + alcohol.getProductType() + ")? ");
      String made = Prompt.inputString("원산지(" + alcohol.getCountryOrigin() + ")? ");
      String grapes = Prompt.inputString("품종(" + alcohol.getVariety() + ")? ");
      float abv = Prompt.inputFloat("알콜도수(" + alcohol.getAlcoholLevel() + ")? ");
      int sweet = checkNum("당도(" + alcohol.getSugerLevel() + ")? ");
      int acidic = checkNum("산도(" + alcohol.getAcidity() + ")? ");
      int body = checkNum("바디감(" + alcohol.getWeight() + ")? ");


      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (input.equalsIgnoreCase("n") || input.length() == 0) {
        System.out.println("회원 변경을 취소하였습니다.");
        return;
      }else if (input.equalsIgnoreCase("y")) {
        alcohol.setProductName(name);
        alcohol.setProductType(kind);
        alcohol.setCountryOrigin(made);
        alcohol.setVariety(grapes);
        alcohol.setAlcoholLevel(abv);
        alcohol.setSugerLevel(sweet);
        alcohol.setAcidity(acidic);
        alcohol.setWeight(body);

        System.out.println("상품정보를 변경하였습니다.");
        return;
      } else {
        System.out.println("유효하지 않음");
        continue;
      }
    }
  }

  public void delete(int auth) {
    if (auth == 0 || auth == 1 ) {
      System.out.println("해당 메뉴는 판매자 권한입니다.");
      return;
    }
    while(true) {
      System.out.println("[상품 삭제]");
      int no = Prompt.inputInt("번호? ");

      Product alcohol = findByNo(no);

      if (alcohol == null) {
        System.out.println("해당 번호의 상품이 없습니다.");
        return;
      }

      String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
      if (input.equalsIgnoreCase("n") || input.length() == 0) {
        System.out.println("상품 삭제를 취소하였습니다.");
        return;
      } else if (input.equalsIgnoreCase("y")) {
        alcoholList.remove(alcohol);   
        System.out.println("상품을 삭제하였습니다.");
        return;
      } else {
        System.out.println("유효하지 않음");
        continue;
      }
    }
  }

  private Product findByNo(int no) {
    Product[] arr = alcoholList.toArray(new Product[0]);
    for (Product alcohol : arr) {
      if (alcohol.getProductNumber() == no) {
        return alcohol;
      }
    }
    return null;
  }

  public boolean exist(String name) {
    Product[] arr = alcoholList.toArray(new Product[0]);
    for (Product Alcohol : arr) {
      if (Alcohol.getProductName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  public String promptAlcohol(String label) {
    while (true) {
      String owner = Prompt.inputString(label);
      if (this.exist(owner)) {
        return owner;
      } else if (owner.length() == 0) {
        return null;
      }
      System.out.println("등록된 상품이 아닙니다.");
    }
  }


  public String promptAlcohols(String label) {
    String Alcohols = "";
    while (true) {
      String Alcohol = Prompt.inputString(label);
      if (this.exist(Alcohol)) {
        if (Alcohols.length() > 0) {
          Alcohols += ",";
        }
        Alcohols += Alcohol;
        continue;
      } else if (Alcohol.length() == 0) {
        break;
      } 
      System.out.println("등록된 상품이 아닙니다.");
    }
    return Alcohols;
  }

  public int checkNum(String label) {
    int number = -1;
    while(true) {
      int num = Prompt.inputInt(label);
      if(num < 1 || num > 5) {  
        System.out.println("1-5 사이 수를 입력해주세요!"); 
        number = num;
        continue;
      }           
      return num;       
    }

  }

}












