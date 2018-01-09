package si.uni_lj.fri.rso.ir_property_listing.cdi;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Health
@ApplicationScoped
public class PropertyListingConfigModifiableHealthCheck implements HealthCheck {
    @Inject
    private Config config;

    @Override
    public HealthCheckResponse call() {
        if (config.getHealthy()) {
            return HealthCheckResponse.named(PropertyListingConfigModifiableHealthCheck.class.getSimpleName()).up().build();
        } else {
            return HealthCheckResponse.named(PropertyListingConfigModifiableHealthCheck.class.getSimpleName()).down().build();
        }
    }
}
