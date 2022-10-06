package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    List<GiftCertificate> findGiftCertificatesByTagListIsContaining(Tag tag);

    boolean existsGiftCertificateByTagListContaining(Tag tag);
}
