package com.eomcs.pms.domain;

import java.sql.Date;

public class Cart {
  private Stock stock; // 장바구니 담을 상품
  private int cartStocks; // 장바구니상품의 갯수
  private int cartPrice; // 총금액
  private int cartNumber; //장바구니번호
  private Date registeredDate;



  public int getCartPrice() {
    return cartPrice;
  }
  public void setCartPrice(int cartPrice) {
    this.cartPrice = stock.getPrice()*getCartStocks();
  }
  public int getCartStocks() {
    return cartStocks;
  }
  public void setCartStocks(int cartStocks) {
    this.cartStocks = cartStocks;
  }
  public Stock getStock() {
    return stock;
  }
  public void setStock(Stock stock) {
    this.stock = stock;
  }
  public int getCartNumber() {
    return cartNumber;
  }
  public void setCartNumber(int cartNumber) {
    this.cartNumber = cartNumber;
  }
  public Date getRegistrationDate() {
    return registeredDate;
  }
  public void setRegistrationDate(Date registeredDate) {
    this.registeredDate = registeredDate;
  }

}