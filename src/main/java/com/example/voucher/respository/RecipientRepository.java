package com.example.voucher.respository;

import com.example.voucher.model.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Recipient findByEmail(String email);
}
