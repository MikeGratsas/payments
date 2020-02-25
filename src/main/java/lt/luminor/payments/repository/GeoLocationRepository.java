package lt.luminor.payments.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.GeoLocation;

@Repository
public interface GeoLocationRepository extends CrudRepository<GeoLocation, Long> {
}
