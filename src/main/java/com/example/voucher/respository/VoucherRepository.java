package com.example.voucher.respository;

import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Voucher findByVoucherCode(String voucherCode);
    Page<Voucher> findByRecipientAndIsUsed(Recipient recipient, boolean isUsed, Pageable pageable);
}
