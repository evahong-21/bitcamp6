package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.pms.App;
import com.eomcs.pms.domain.Cart;
import com.eomcs.util.Prompt;

public class CartUpdateHandler extends AbstractCartHandler {

  public CartUpdateHandler(List<Cart> cartList) {
    super(cartList);
  }

  public void execute() {
    if (App.getLoginUser().getAuthority() != 1) {
      System.out.println("권한이 없습니다. 구매자 기능입니다.");
      return;
    }
    System.out.println("[장바구니 변경]");
    Cart cart = findByCart(Prompt.inputString("상품명 : "));

    if (cart == null) {
      System.out.println("장바구니에 해당 상품이 없습니다.");
      return;
    }

    //변경할 수 있는 것 - 수량? 
    int cartstocks = Prompt.inputInt("변경 후의 수량 : " + cart.getCartStocks() + ")? ");
    int cartPrice = 0;

    String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
    if (input.equalsIgnoreCase("n") || input.length() == 0) {
      System.out.println("장바구니 변경을 취소하였습니다.");
      return;
    }
    cart.setCartStocks(cartstocks);
    //자동으로 변경되어야할 것 - 가격
    cart.setCartPrice(cartPrice);

    System.out.println("장바구니를 변경하였습니다.");
  }

}
