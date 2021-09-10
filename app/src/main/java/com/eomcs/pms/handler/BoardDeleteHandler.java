package com.eomcs.pms.handler;

import java.util.List;
import com.eomcs.menu.Menu;
import com.eomcs.pms.App;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.Prompt;

public class BoardDeleteHandler extends AbstractBoardHandler {

  public BoardDeleteHandler(List<Board> boardList) {
    super(boardList);
  }

  @Override
  public void execute() {

    System.out.println("[게시글 삭제]");
    int no = Prompt.inputInt("삭제할 게시글 번호 : ");

    Board board = findByNo(no);

    if (board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }

    if ((board.getWriter() != App.getLoginUser().getId()) &
        (App.getLoginUser().getAuthority() != Menu.ACCESS_ADMIN)) {
      System.out.println("작성자가 아니므로 삭제할 수 없습니다.");
      return;
    }

    String input = Prompt.inputString("정말 삭제하시겠습니까?(y/N) ");
    if (input.equalsIgnoreCase("y")) {
      boardList.remove(board);
      System.out.println("게시글을 삭제하였습니다.");
      return;
    }
    System.out.println("게시글 삭제를 취소하였습니다.");
    return;
  }

}






