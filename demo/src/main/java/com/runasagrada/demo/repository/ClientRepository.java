package com.runasagrada.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.runasagrada.demo.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

        @Query("""
                            SELECT c FROM Client c
                            WHERE (:email IS NULL OR c.user.email LIKE %:email%)
                              AND (:phone IS NULL OR c.user.phone LIKE %:phone%)
                              AND (:nationalId IS NULL OR c.user.nationalId LIKE %:nationalId%)
                              AND (:name IS NULL OR c.user.name LIKE %:name%)
                        """)
        List<Client> findByCriteria(
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("nationalId") String nationalId,
                        @Param("name") String name);

        @Query("""
                            SELECT c FROM Client c
                            WHERE (c.user.email = :email)
                              AND (c.user.password = :password)
                        """)
        Client login(@Param("email") String email, @Param("password") String password);

}
