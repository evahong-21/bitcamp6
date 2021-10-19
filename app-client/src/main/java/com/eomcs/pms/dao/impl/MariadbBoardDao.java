package com.eomcs.pms.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.eomcs.pms.dao.BoardDao;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Comment;
import com.eomcs.pms.domain.Member;

public class MariadbBoardDao implements BoardDao{

  Connection con;

  public MariadbBoardDao(Connection con) {
    this.con =  con;
  }

  @Override
  public void insert(Board board) throws Exception {
    try(PreparedStatement stmt = con.prepareStatement(
        "insert"
            + " into board(member_no,title,content)"
            + " values(?,?,?)")){

      stmt.setInt(1, board.getWriter().getNumber());
      stmt.setString(2, board.getTitle());
      stmt.setString(3, board.getContent());


      if(stmt.executeUpdate() == 0) {
        throw new Exception("게시글 저장 실패");
      }
    }

  }


  @Override
  public List<Board> findAll() throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " b.board_no,"
            + " b.member_no,"
            + " b.title,"
            + " b.content,"
            + " b.registeredDate,"
            + " b.views, "
            + " m.name, "
            + " m.id "
            + " from board b inner join member m"
            + " on b.member_no=m.member_no"
            + " order by b.board_no desc");
        ResultSet rs = stmt.executeQuery()) {

      ArrayList<Board> list = new ArrayList<>();

      while (rs.next()) {
        Board board = new Board();
        board.setBoardNumber(rs.getInt("board_no"));
        board.setTitle(rs.getString("title"));

        Member member = new Member();
        member.setNumber(rs.getInt("member_no"));
        member.setName(rs.getString("name"));
        member.setId(rs.getString("id"));
        board.setWriter(member);

        board.setViews(rs.getInt("views"));
        board.setRegistrationDate(rs.getDate("registeredDate"));

        list.add(board);
      }

      return list;
    }
  }

  @Override
  public Board findByNo(int no) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "select"
            + " b.board_no,b.title,b.content,b.registeredDate,b.views,"
            + " m.member_no, m.id, m.name,m.email"
            + " from board b inner join member m on b.member_no=m.member_no"
            + " where board_no=" + no);
        ResultSet rs = stmt.executeQuery()) {

      if (rs.next()) {
        Board board = new Board();
        board.setBoardNumber(rs.getInt("board_no"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setRegistrationDate(rs.getDate("registeredDate"));
        board.setViews(rs.getInt("views"));

        Member member = new Member();
        member.setNumber(rs.getInt("member_no"));
        member.setId(rs.getString("id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));

        board.setWriter(member);

        // 조회수 증가하기
        try (PreparedStatement stmt2 = con.prepareStatement(
            "update board set views=views + 1"
                + " where board_no=" + no)) {
          stmt2.executeUpdate();
        }

        return board;
      }

      return null;
    }
  }

  @Override
  public Board findByBoard(String keyword) throws Exception {
    //    HashMap<String, String> params = new HashMap<>();
    //    params.put("keyword", keyword);
    //
    //    requestAgent.request("board.selectOne", params);
    //
    //    if(requestAgent.getStatus().equals(RequestAgent.FAIL)){
    //      return null;
    //    }
    //    return requestAgent.getObject(Board.class);
    return null;
  }

  @Override
  public void update(Board board) throws Exception {
    try (PreparedStatement stmt = con.prepareStatement(
        "update board set"
            + " title=?,content=?"
            + " where board_no=?")) {

      stmt.setString(1, board.getTitle());
      stmt.setString(2, board.getContent());
      stmt.setInt(3, board.getBoardNumber());

      if (stmt.executeUpdate() == 0) {
        throw new Exception("게시글 데이터 변경 실패!");
      }
    }
  }

  @Override
  public void delete(int no) throws Exception { 
    try (PreparedStatement stmt = con.prepareStatement(
        "delete from board where board_no="+no)) {

      if (stmt.executeUpdate() == 0) {
        throw new Exception("게시글 데이터 삭제 실패!");
      }
    }
  }

  // 댓글 등록
  @Override
  public void insert(Comment comment) throws Exception {
    //    requestAgent.request("board.comment.insert", comment);
    //    if(requestAgent.getStatus().equals(RequestAgent.FAIL)){
    //      throw new Exception("댓글 데이터 저장 실패");   }

  }

  // 댓글 조회
  @Override
  public List<Comment> findAll(int boardNo) throws Exception {
    //    HashMap<String, String> params = new HashMap<>();
    //    params.put("boardNo", String.valueOf(boardNo));
    //
    //    requestAgent.request("board.comment.selectList", params);
    //    if (requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //      System.out.println(requestAgent.getObject(String.class));
    //      return null;
    //    }
    //    return new ArrayList<>(requestAgent.getObjects(Comment.class));
    return null;
  }

  // 댓글 변경
  @Override
  public void update(Comment comment) throws Exception {
    //    requestAgent.request("board.comment.update", comment);
    //
    //    if(requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //      //      System.out.println(requestAgent.getObject(String.class));
    //      throw new Exception("댓글 데이터 변경 실패");
    //    }
  }

  // 댓글 삭제
  @Override
  public void delete(Comment comment) throws Exception {
    //    requestAgent.request("board.comment.delete", comment);
    //
    //    if(requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //      throw new Exception("댓글 데이터 삭제 실패");
    //    }
  }

  // 댓글 선택
  @Override
  public Comment findCommentByNo(int boardNo, int commentNo) throws Exception {
    //    HashMap<String, String> params = new HashMap<>();
    //    params.put("boardNo", String.valueOf(boardNo));
    //    params.put("commentNo", String.valueOf(commentNo));
    //
    //    requestAgent.request("board.comment.selectOne", params);
    //    if(requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //      return null;
    //    }
    //    return requestAgent.getObject(Comment.class);
    return null;
  }

  @Override
  public void like(Board board) throws Exception {
    //
    //    requestAgent.request("board.comment.like", board);
    //
    //    if(requestAgent.getStatus().equals(RequestAgent.FAIL)) {
    //      throw new Exception("좋아요 데이터 변경 실패");
    //    }
  }
}
