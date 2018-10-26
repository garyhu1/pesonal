package com.garyhu.repository;

import com.garyhu.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author: garyhu
 * @since: 2018/10/26 0026
 * @decription:
 */
@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
