package ru.itex.repository;

/**
 *
 * @author tinz
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itex.entity.AddressEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>{
    
}
