package com.eomcs.pms.handler;

import java.sql.Date;
import java.util.List;
import com.eomcs.menu.Menu;
import com.eomcs.pms.App;
import com.eomcs.pms.domain.SellerPrivacy;
import com.eomcs.util.Prompt;

public class SellerPrivacyUpdateHandler extends AbstractSellerPrivacyHandler {

  public SellerPrivacyUpdateHandler(List<SellerPrivacy> memberList) {
    super(memberList);
  }


  @Override
  public void execute() {
    if (App.getLoginUser().getAuthority() == Menu.ACCESS_LOGOUT) {
      System.out.println("해당 메뉴는 로그인 후 사용가능합니다.\n로그인 후 사용해주세요.");
      return;
    }

    if (App.getLoginUser().getAuthority() != Menu.ACCESS_ADMIN) {
      System.out.println("\n[개인정보 변경]");

      SellerPrivacy member = findById(App.getLoginUser().getId());

      //    String name = Prompt.inputString(String.format("이름(변경 전 : %s) : ", member.getName()));
      String nickName = Prompt.inputString(String.format("닉네임(변경 전 : %s) : ", member.getNickname()));
      String email = Prompt.inputString(String.format("이메일(변경 전 : %s) : ", member.getEmail()));
      Date birthDay = Prompt.inputDate(String.format("생일(변경 전 : %s) : ", member.getBirthday()));
      String password = Prompt.inputString(String.format("암호(변경 전 : %s) : ", member.getName()));
      String photo = Prompt.inputString(String.format("사진(변경 전 : %s) : ", member.getPhoto()));
      String tel = Prompt.inputString(String.format("전화(변경 전 : %s) : ", member.getPhoneNumber()));
      String bussinessNo = Prompt.inputString(String.format("사업자번호(변경 전 : %s) : ", member.getBusinessNumber()));
      String bussinessAddress = Prompt.inputString(String.format("사업장주소(변경 전 : %s) : ", member.getBusinessAddress()));
      String bussinessTel = Prompt.inputString(String.format("사업장번호(변경 전 : %s) : ", member.getBusinessPlaceNumber()));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");

      if (input.equalsIgnoreCase("y")) {     
        //        member.setName(name);
        member.setNickname(nickName);
        member.setEmail(email);
        member.setBirthday(birthDay);
        member.setPassword(password);
        member.setPhoto(photo);
        member.setPhoneNumber(tel);
        member.setBusinessNumber(bussinessNo);
        member.setBusinessAddress(bussinessAddress);
        member.setBusinessPlaceNumber(bussinessTel);  
        System.out.println("개인 정보를 변경하였습니다.");
        return;
      } else {
        System.out.println("개인 정보 변경을 취소하였습니다.");
        return;
      }
    } else {
      System.out.println("\n[판매자 변경]");
      String id = Prompt.inputString("변경할 판매자 번호: ");

      SellerPrivacy member = findById(id);

      if (member == null) {
        System.out.println("해당 번호의 회원이 없습니다.");
        return;
      }
      // 닉네임, 레벨, 판매자/구매자(회원) 변경 가능
      String nickName = Prompt.inputString(String.format("닉네임(변경 전 : %s)", member.getNickname()));
      int level = Prompt.inputInt(String.format("등급(변경 전 : %d)", member.getLevel()));
      //      String buyerSeller = Prompt.inputString("구매자/판매자(" + member.getBuyerSeller()  + ")? ");
      // 관리자가 판매자를 구매자로 변경시키는 방법 구현 예정

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (input.equalsIgnoreCase("y")) {
        member.setNickname(nickName);
        member.setLevel(level);
        //      member.setBuyerSeller(buyerSeller);
        System.out.println("회원 변경(판매자)을 완료하였습니다.");
        return;
      }
      System.out.println("회원 변경(판매자)을 취소하였습니다.");
      return;
    }
  }
}







