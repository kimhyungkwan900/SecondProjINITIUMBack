package com.secondprojinitiumback.admin.Mileage.repository;

import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MileageTotalRepository extends JpaRepository<MileageTotal, String> {

}
