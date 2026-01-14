package com.szwedo.krystian.pds.repository;

import com.szwedo.krystian.pds.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, String> {
}
