package com.eomcs.pms;

import static com.eomcs.menu.Menu.ACCESS_ADMIN;
import static com.eomcs.menu.Menu.ACCESS_BUYER;
import static com.eomcs.menu.Menu.ACCESS_LOGOUT;
import static com.eomcs.menu.Menu.ACCESS_SELLER;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.eomcs.context.ApplicationContextListener;
import com.eomcs.menu.Menu;
import com.eomcs.menu.MenuGroup;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.BookingList;
import com.eomcs.pms.domain.Buyer;
import com.eomcs.pms.domain.CartList;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.MessageList;
import com.eomcs.pms.domain.Product;
import com.eomcs.pms.domain.Seller;
import com.eomcs.pms.domain.StockList;
import com.eomcs.pms.handler.BoardAddHandler;
import com.eomcs.pms.handler.BoardDeleteHandler;
import com.eomcs.pms.handler.BoardDetailHandler;
import com.eomcs.pms.handler.BoardDetailHandler2;
import com.eomcs.pms.handler.BoardFindHandler;
import com.eomcs.pms.handler.BoardListHandler;
import com.eomcs.pms.handler.BoardPrompt;
import com.eomcs.pms.handler.BoardSearchHandler;
import com.eomcs.pms.handler.BoardSearchHandler2;
import com.eomcs.pms.handler.BoardUpdateHandler;
import com.eomcs.pms.handler.BookingAddHandler;
import com.eomcs.pms.handler.BookingDeleteHandler;
import com.eomcs.pms.handler.BookingDetailHandler;
import com.eomcs.pms.handler.BookingListHandler;
import com.eomcs.pms.handler.BookingPrompt;
import com.eomcs.pms.handler.BookingUpdateHandler;
import com.eomcs.pms.handler.BuyerAddHandler;
import com.eomcs.pms.handler.BuyerDeleteHandler;
import com.eomcs.pms.handler.BuyerDetailHandler;
import com.eomcs.pms.handler.BuyerListHandler;
import com.eomcs.pms.handler.BuyerUpdateHandler;
import com.eomcs.pms.handler.CartAddHandler;
import com.eomcs.pms.handler.CartDeleteHandler;
import com.eomcs.pms.handler.CartDetailHandler;
import com.eomcs.pms.handler.CartListHandler;
import com.eomcs.pms.handler.CartPrompt;
import com.eomcs.pms.handler.CartUpdateHandler;
import com.eomcs.pms.handler.Command;
import com.eomcs.pms.handler.CommandRequest;
import com.eomcs.pms.handler.CommentAddHandler;
import com.eomcs.pms.handler.CommentDeleteHandler;
import com.eomcs.pms.handler.CommentFindHandler;
import com.eomcs.pms.handler.CommentListHandler;
import com.eomcs.pms.handler.CommentUpdateHandler;
import com.eomcs.pms.handler.FindIdHandler;
import com.eomcs.pms.handler.FindPasswordHandler;
import com.eomcs.pms.handler.LikeHandler;
import com.eomcs.pms.handler.LoginHandler;
import com.eomcs.pms.handler.MemberPrompt;
import com.eomcs.pms.handler.MessageAddHandler;
import com.eomcs.pms.handler.MessageDeleteHandler;
import com.eomcs.pms.handler.MessageDetailHandler;
import com.eomcs.pms.handler.MessageListHandler;
import com.eomcs.pms.handler.MessagePrompt;
import com.eomcs.pms.handler.MessageUpdateHandler;
import com.eomcs.pms.handler.ProductAddHandler;
import com.eomcs.pms.handler.ProductDeleteHandler;
import com.eomcs.pms.handler.ProductDetailHandler;
import com.eomcs.pms.handler.ProductDetailHandler2;
import com.eomcs.pms.handler.ProductListHandler;
import com.eomcs.pms.handler.ProductPrompt;
import com.eomcs.pms.handler.ProductSearchHandler;
import com.eomcs.pms.handler.ProductUpdateHandler;
import com.eomcs.pms.handler.RankingHandler;
import com.eomcs.pms.handler.ReviewAddHandler;
import com.eomcs.pms.handler.ReviewDeleteHandler;
import com.eomcs.pms.handler.ReviewFindHandler;
import com.eomcs.pms.handler.ReviewListHandler;
import com.eomcs.pms.handler.ReviewUpdateHandler;
import com.eomcs.pms.handler.SellerAddHandler;
import com.eomcs.pms.handler.SellerDeleteHandler;
import com.eomcs.pms.handler.SellerDetailHandler;
import com.eomcs.pms.handler.SellerListHandler;
import com.eomcs.pms.handler.SellerUpdateHandler;
import com.eomcs.pms.handler.StockAddHandler;
import com.eomcs.pms.handler.StockDeleteHandler;
import com.eomcs.pms.handler.StockDetailHandler;
import com.eomcs.pms.handler.StockListHandler;
import com.eomcs.pms.handler.StockPrompt;
import com.eomcs.pms.handler.StockUpdateHandler;
import com.eomcs.pms.listener.AppInitListener;
import com.eomcs.pms.listener.FileListener;
import com.eomcs.util.Prompt;

public class App {
  public static final int MEMBER_NUMBER_INDEX = 0;
  public static final int BOARD_NUMBER_INDEX = 1;
  public static final int PROUDCT_NUMBER_INDEX = 2;

  List<Board> boardList = new ArrayList<>();
  List<Product> productList = new ArrayList<>();
  List<StockList> allStockList = new ArrayList<>();
  List<BookingList> allBookingList = new ArrayList<>();
  List<CartList> allCartList = new ArrayList<>();
  List<Member> memberList = new ArrayList<>();
  List<Member> deletedMemberList = new ArrayList<>();
  List<Buyer> buyerList = new ArrayList<>();
  List<Seller> sellerList = new ArrayList<>();
  List<MessageList> allMessageList = new ArrayList<>();
  List<Integer> totalNumberList = new ArrayList<>();// totalMemberNumber, totalBoardNumber, totalProductNumber

  HashMap<String, Command> commandMap = new HashMap<>();
  ProductPrompt productPrompt = new ProductPrompt(productList);
  LoginHandler loginHandler = new LoginHandler(memberList);
  MemberPrompt memberPrompt = new MemberPrompt(memberList, deletedMemberList);
  StockPrompt stockPrompt = new StockPrompt(allStockList, memberPrompt);
  BookingPrompt bookingPrompt = new BookingPrompt(allBookingList);
  CartPrompt cartPrompt = new CartPrompt(allCartList, memberPrompt);
  BoardPrompt boardPrompt = new BoardPrompt(boardList);
  MessagePrompt messagePrompt = new MessagePrompt(allMessageList);
  // 리스너
  List<ApplicationContextListener> listeners = new ArrayList<>();

  public void addApplicationContextListener(ApplicationContextListener listener) {
    this.listeners.add(listener);
  }
  public void removeApplicationContextListener(ApplicationContextListener listener) {
    this.listeners.remove(listener);
  }

  // 권한에 따른 메뉴 구성 위함.
  class MenuItem extends Menu {
    String menuId;
    public MenuItem(String title, String menuId) {
      this(title, ACCESS_LOGOUT | ACCESS_BUYER | ACCESS_SELLER | ACCESS_ADMIN , menuId);
    }

    public MenuItem(String title, int accessScope, String menuId) {
      super(title, accessScope);
      this.menuId = menuId;
    }
    @Override
    public void execute() {
      Command command  = commandMap.get(menuId);
      try {
        command.execute(new CommandRequest(commandMap));
      } catch (Exception e) {
        System.out.printf("%s 명령을 실행하는 중 오류 발생!\n",  menuId);
        e.printStackTrace();
      }
    }
  }

  // 현재 로그인한 정보를 저장 (id, pw, auth)
  public static Member loginMember = new Member();
  public static Member getLoginUser() {
    return loginMember;
  }

  public static void main(String[] args) {
    App app = new App(); 

    app.addApplicationContextListener(new AppInitListener());
    app.addApplicationContextListener(new FileListener());
    app.service();
  }

  public App() {

    commandMap.put("/buyer/add",    new BuyerAddHandler(memberList, deletedMemberList, cartPrompt, bookingPrompt, memberPrompt,totalNumberList ,messagePrompt));
    commandMap.put("/buyer/list",   new BuyerListHandler(memberList));
    commandMap.put("/buyer/detail", new BuyerDetailHandler(memberList));
    commandMap.put("/buyer/update", new BuyerUpdateHandler(memberList));
    commandMap.put("/buyer/delete", new BuyerDeleteHandler(memberList, deletedMemberList, memberPrompt, cartPrompt, bookingPrompt, messagePrompt));

    commandMap.put("/seller/add",    new SellerAddHandler(memberList, deletedMemberList, memberPrompt, bookingPrompt, stockPrompt, totalNumberList, messagePrompt));
    commandMap.put("/seller/list",   new SellerListHandler(memberList));
    commandMap.put("/seller/detail", new SellerDetailHandler(memberList));
    commandMap.put("/seller/update", new SellerUpdateHandler(memberList));
    commandMap.put("/seller/delete", new SellerDeleteHandler(memberList, deletedMemberList, memberPrompt, bookingPrompt, stockPrompt, messagePrompt));

    commandMap.put("/board/add",    new BoardAddHandler(boardList, totalNumberList));
    commandMap.put("/board/list",   new BoardListHandler(boardList));
    commandMap.put("/board/detail", new BoardDetailHandler(boardList, boardPrompt, memberPrompt));
    commandMap.put("/board/detail2", new BoardDetailHandler2(boardList, boardPrompt, memberPrompt));
    commandMap.put("/board/update", new BoardUpdateHandler(boardList));
    commandMap.put("/board/delete", new BoardDeleteHandler(boardList));
    commandMap.put("/board/search", new BoardSearchHandler(boardList));
    commandMap.put("/board/search2", new BoardSearchHandler2(boardList));

    commandMap.put("/comment/like",    new LikeHandler(boardPrompt));
    commandMap.put("/comment/add",    new CommentAddHandler(boardPrompt));
    commandMap.put("/comment/list",    new CommentListHandler(boardPrompt));
    commandMap.put("/comment/update",    new CommentUpdateHandler(boardPrompt));
    commandMap.put("/comment/delete",    new CommentDeleteHandler(boardPrompt));


    commandMap.put("/product/add",    new ProductAddHandler(productList, productPrompt, totalNumberList));
    commandMap.put("/product/list",   new ProductListHandler(productList, productPrompt));
    commandMap.put("/product/search", new ProductSearchHandler(productPrompt, stockPrompt, memberPrompt, cartPrompt, productList));
    commandMap.put("/product/detail", new ProductDetailHandler(productPrompt));
    commandMap.put("/product/detail2", new ProductDetailHandler2(productPrompt));

    commandMap.put("/product/update", new ProductUpdateHandler(productPrompt));
    commandMap.put("/product/delete", new ProductDeleteHandler(productPrompt, productList));

    commandMap.put("/review/add", new ReviewAddHandler(productPrompt));
    commandMap.put("/review/list", new ReviewListHandler(productPrompt));
    commandMap.put("/review/update", new ReviewUpdateHandler(productPrompt, productList));
    commandMap.put("/review/delete", new ReviewDeleteHandler(productPrompt));

    commandMap.put("/stock/add"  ,  new StockAddHandler(allStockList, stockPrompt,productPrompt));
    commandMap.put("/stock/list",   new StockListHandler(allStockList, stockPrompt));
    commandMap.put("/stock/detail", new StockDetailHandler(stockPrompt));
    commandMap.put("/stock/update", new StockUpdateHandler(stockPrompt));
    commandMap.put("/stock/delete", new StockDeleteHandler(stockPrompt));

    commandMap.put("/cart/add"  ,  new CartAddHandler(allCartList, cartPrompt, stockPrompt, memberPrompt));
    commandMap.put("/cart/list",   new CartListHandler(allCartList, cartPrompt, memberPrompt));
    commandMap.put("/cart/detail", new CartDetailHandler(cartPrompt));
    commandMap.put("/cart/update", new CartUpdateHandler(cartPrompt));
    commandMap.put("/cart/delete", new CartDeleteHandler(cartPrompt));

    commandMap.put("/booking/add",    new BookingAddHandler(allBookingList, cartPrompt, stockPrompt, bookingPrompt, memberPrompt));
    commandMap.put("/booking/list",   new BookingListHandler(allBookingList, bookingPrompt, memberPrompt));
    commandMap.put("/booking/detail",   new BookingDetailHandler(allBookingList, bookingPrompt, memberPrompt));
    commandMap.put("/booking/update", new BookingUpdateHandler(allBookingList, bookingPrompt, stockPrompt, memberPrompt));
    commandMap.put("/booking/delete", new BookingDeleteHandler(allBookingList, bookingPrompt));

    commandMap.put("/findId", new FindIdHandler(memberPrompt));
    commandMap.put("/findPassword", new FindPasswordHandler(memberPrompt));

    commandMap.put("/findBoard", new BoardFindHandler(boardList, boardPrompt, memberPrompt));
    commandMap.put("/findComment", new CommentFindHandler(boardList, boardPrompt, memberPrompt));

    commandMap.put("/findReview", new  ReviewFindHandler(productPrompt, productList));

    commandMap.put("/ranking/list", new RankingHandler(productList, productPrompt));

    commandMap.put("/message/add",    new MessageAddHandler(allMessageList, memberPrompt));
    commandMap.put("/message/update",    new MessageUpdateHandler(allMessageList, memberPrompt));
    commandMap.put("/message/list",   new MessageListHandler(allMessageList, memberPrompt));
    commandMap.put("/message/detail", new MessageDetailHandler(allMessageList));
    commandMap.put("/message/delete", new MessageDeleteHandler(allMessageList));
  }

  private void notifyOnApplicationStarted() {
    HashMap<String, Object> params = new HashMap<>();
    params.put("boardList", boardList);
    params.put("productList", productList);
    params.put("allStockList", allStockList);
    params.put("allBookingList", allBookingList);
    params.put("allCartList", allCartList);
    params.put("memberList", memberList);
    params.put("buyerList", buyerList);
    params.put("sellerList", sellerList);
    params.put("allMessageList", allMessageList);
    params.put("totalNumberList", totalNumberList);


    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(params);
    }
  }

  private void notifyOnApplicationEnded() {
    HashMap<String, Object> params = new HashMap<>();
    params.put("boardList", boardList);
    params.put("productList", productList);
    params.put("allStockList", allStockList);
    params.put("allBookingList", allBookingList);
    params.put("allCartList", allCartList);
    params.put("memberList", memberList);
    params.put("buyerList", buyerList);
    params.put("sellerList", sellerList);
    params.put("allMessageList", allMessageList);
    params.put("totalNumberList", totalNumberList);


    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(params);
    }
  }

  void service() {
    notifyOnApplicationStarted();

    memberList.add(new Member("관리자","1234", Menu.ACCESS_ADMIN));
    if (totalNumberList.size() == 0) {
      totalNumberList.add(MEMBER_NUMBER_INDEX, 1); 
      totalNumberList.add(BOARD_NUMBER_INDEX, 1); 
      totalNumberList.add(PROUDCT_NUMBER_INDEX, 1);
    }

    System.out.println();

    createMenu().execute();
    Prompt.close();

    notifyOnApplicationEnded();

  }

  Menu createMenu() {

    MenuGroup mainMenuGroup = new MenuGroup("메인");
    mainMenuGroup.setPrevMenuTitle("종료");


    mainMenuGroup.add(new Menu("로그인", ACCESS_LOGOUT) {
      @Override
      public void execute() {
        Member prv = loginHandler.InputId(); 
        if (prv==null) {
          System.out.println("아이디(비밀번호)를 다시 확인하시기 바랍니다.");
        } else {
          loginMember = prv;
        }
      }});

    mainMenuGroup.add(new Menu("로그아웃", ACCESS_BUYER | ACCESS_ADMIN | ACCESS_SELLER) {
      @Override
      public void execute() {
        if ( loginMember.getAuthority()!= 0) {
          loginMember = new Member(); 
          System.out.println("로그아웃이 완료되었습니다."); 
        } else {
          System.out.println("로그인 후 사용해주세요");
        }
      }});

    ///////////////////////////////////////////

    //    MenuGroup rankingMenu = new MenuGroup("실시간 랭킹");
    mainMenuGroup.add(new MenuItem("실시간 랭킹",  "/ranking/list"));

    ///////////////////////////////////////////

    //    MenuGroup boardMenu = new MenuGroup("게시판");
    mainMenuGroup.add(new MenuItem("게시판", "/board/list"));

    ///////////////////////////////////////////

    mainMenuGroup.add(new MenuItem("상품", "/product/list"));

    //    MenuGroup productMenu = new MenuGroup("상품");
    //    mainMenuGroup.add(productMenu);
    //
    //    productMenu.add(new MenuItem("등록", ACCESS_ADMIN | ACCESS_SELLER, "/product/add"));
    //    productMenu.add(new MenuItem("상품", "/product/list"));
    //    productMenu.add(new MenuItem("상품검색",  "/product/search"));

    ///////////////////////////////////////////

    //    MenuGroup cartMenu = new MenuGroup("장바구니", ACCESS_BUYER );
    mainMenuGroup.add(new MenuItem("장바구니",  ACCESS_BUYER, "/cart/list"));

    ///////////////////////////////////////////

    //    MenuGroup bookingMenu = new MenuGroup("픽업예약", ACCESS_BUYER | ACCESS_SELLER);
    mainMenuGroup.add(new MenuItem("예약내역",  ACCESS_BUYER | ACCESS_SELLER, "/booking/list"));

    ///////////////////////////////////////////

    MenuGroup joinMenu = new MenuGroup("회원가입", ACCESS_LOGOUT);
    mainMenuGroup.add(joinMenu);

    joinMenu.add(new MenuItem("일반회원", "/buyer/add"));
    joinMenu.add(new MenuItem("판매자", "/seller/add"));

    MenuGroup findMenu = new MenuGroup("아이디/비번 찾기", ACCESS_LOGOUT);
    mainMenuGroup.add(findMenu);

    findMenu.add(new MenuItem("아이디찾기", "/findId"));
    findMenu.add(new MenuItem("비밀번호찾기", "/findPassword"));

    ////////////////////////////////////////////

    MenuGroup personMenu = new MenuGroup("프로필", ACCESS_BUYER | ACCESS_SELLER);
    mainMenuGroup.add(personMenu);

    personMenu.add(new MenuItem("My Store", ACCESS_SELLER, "/stock/list") {
      @Override
      public void execute() {
        Member mine = memberPrompt.findById(App.getLoginUser().getId());
        System.out.printf("<<\t%s\t>>\n", ((Seller) mine).getBusinessName());
        System.out.printf("> 주소\t:\t%s\n", ((Seller) mine).getBusinessAddress());
        System.out.printf("> 전화번호\t:\t%s\n", ((Seller) mine).getBusinessPlaceNumber());
        System.out.println();
        Command command  = commandMap.get(menuId);
        try {
          command.execute(new CommandRequest(commandMap));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }});

    personMenu.add(new MenuItem("개인정보", ACCESS_BUYER, "/buyer/detail"));
    personMenu.add(new MenuItem("개인정보", ACCESS_SELLER, "/seller/detail"));
    personMenu.add(new MenuItem("내 게시글", "/findBoard"));
    personMenu.add(new MenuItem("내 댓글", "/findComment"));
    personMenu.add(new MenuItem("내 리뷰", "/findReview"));


    MenuGroup managerMenu = new MenuGroup("관리자모드", ACCESS_ADMIN );
    mainMenuGroup.add(managerMenu);

    //    MenuGroup managerMemberMenu1 = new MenuGroup("일반회원관리"); //1
    managerMenu.add(new MenuItem("일반회원관리", "/buyer/list"));

    //    MenuGroup managerSellerMenu1 = new MenuGroup("판매자관리");  //2
    managerMenu.add(new MenuItem("판매자관리", "/seller/list"));

    //    MenuGroup messageMenu = new MenuGroup("메세지", ACCESS_BUYER | ACCESS_ADMIN | ACCESS_SELLER);
    mainMenuGroup.add(new MenuItem("메세지", ACCESS_BUYER | ACCESS_ADMIN | ACCESS_SELLER, "/message/list"));

    return mainMenuGroup;
  }

  public static String level(int i) {
    switch (i) {
      case Menu.ACCESS_LOGOUT : return "비회원";
      case Menu.ACCESS_BUYER : return "일반회원";
      case Menu.ACCESS_SELLER : return "판매자";
      default : return "관리자";
    }
  }

}