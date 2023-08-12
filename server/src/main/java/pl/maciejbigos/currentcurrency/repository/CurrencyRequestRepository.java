package pl.maciejbigos.currentcurrency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maciejbigos.currentcurrency.model.CurrencyRequest;

@Repository
public interface CurrencyRequestRepository extends JpaRepository<CurrencyRequest, Long> {
}
