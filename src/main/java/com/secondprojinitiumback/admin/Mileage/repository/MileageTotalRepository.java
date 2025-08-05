package com.secondprojinitiumback.admin.Mileage.repository;

import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 해당 Entitiy 키 변수형이 Student이므로 Student를 제네릭으로 사용
public interface MileageTotalRepository extends JpaRepository<MileageTotal, Student> {

}
