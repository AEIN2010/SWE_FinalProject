package com.example.caraucbackend.repos;


import com.example.caraucbackend.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepo extends JpaRepository<Bid, Long> {

    public List<Bid> findAllByBidderUsernameIs(String username);
}
