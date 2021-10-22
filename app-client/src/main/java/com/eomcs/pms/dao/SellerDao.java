package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Seller;

public interface SellerDao {
  void insert(Member seller) throws Exception;
  List<Seller> findAll() throws Exception;
  Seller findById(String id) throws Exception;
  void update(Seller seller) throws Exception;
  void delete(Seller seller) throws Exception;
  void insertSeller(Seller seller) throws Exception;
}
