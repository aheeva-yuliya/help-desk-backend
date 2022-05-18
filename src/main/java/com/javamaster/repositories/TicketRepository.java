package com.javamaster.repositories;

import com.javamaster.entities.Ticket;
import com.javamaster.entities.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select t from Ticket t where t.owner.id = ?1 " +
            "order by t.urgency.id, t.desiredResolutionDate")
    List<Ticket> findAllByEmployeeIdOrderByUrgencyIdDesiredResolutionDate(Integer userId);

    @Query("select t from Ticket t where t.owner.id = :id or t.state <> :state " +
            "order by t.urgency.id, t.desiredResolutionDate")
    List<Ticket> findAllByManagerIdOrderByUrgencyIdDesiredResolutionDate(@Param("id") Integer userId,
                                                                         @Param("state") State state);

    @Query("select t from Ticket t where t.state = :state1 or t.state = :state2 or t.state = :state3 " +
            "order by t.urgency.id, t.desiredResolutionDate")
    List<Ticket> findAllByEngineerIdOrderByUrgencyIdDesiredResolutionDate(@Param("state1") State state1,
                                                                          @Param("state2") State state2,
                                                                          @Param("state3") State state3);

}
