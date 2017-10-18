package ru.itex.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itex.entity.OptionEntity;
import ru.itex.vo.OptionVO;

/**
 * Created by devil4lived@gmail.com on 25.01.2017.
 */
@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    @Query(nativeQuery = true, value = "select o.value from qa.option o where o.key=:key")
    String getValue(@Param("key") String key);

    @Query(nativeQuery = true, value = "select o from qa.option o where o.key=:key")
    OptionVO getOption(@Param("key") String key);
}
