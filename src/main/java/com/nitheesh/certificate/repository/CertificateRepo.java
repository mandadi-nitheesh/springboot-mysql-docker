package com.nitheesh.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nitheesh.certificate.entity.CertificateData;

@Repository
public interface CertificateRepo extends JpaRepository<CertificateData,String> {

}
