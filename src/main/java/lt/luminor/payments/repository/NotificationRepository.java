package lt.luminor.payments.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import lt.luminor.payments.entity.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
