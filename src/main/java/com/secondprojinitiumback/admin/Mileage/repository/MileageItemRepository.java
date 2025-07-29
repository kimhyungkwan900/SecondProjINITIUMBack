package com.secondprojinitiumback.admin.Mileage.repository;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileageItemRepository extends JpaRepository<MileageItem, Long> {

}
