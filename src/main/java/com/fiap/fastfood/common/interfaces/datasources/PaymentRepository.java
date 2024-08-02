package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.PaymentORM;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface PaymentRepository extends CrudRepository<PaymentORM, String> {

    List<PaymentORM> findAll();
}
