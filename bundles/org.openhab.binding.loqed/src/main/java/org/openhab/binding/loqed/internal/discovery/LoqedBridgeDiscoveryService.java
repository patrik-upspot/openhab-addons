/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.loqed.internal.discovery;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jmdns.ServiceInfo;

import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.loqed.internal.constants.LoqedBindingConstants;
import org.openhab.core.config.discovery.DiscoveryResult;
import org.openhab.core.config.discovery.DiscoveryResultBuilder;
import org.openhab.core.config.discovery.mdns.MDNSDiscoveryParticipant;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Patrik Schlinker - Initial contribution
 */
@Component(service = MDNSDiscoveryParticipant.class)
public class LoqedBridgeDiscoveryService implements MDNSDiscoveryParticipant {
    private final Logger logger = LoggerFactory.getLogger(LoqedBridgeDiscoveryService.class);

    /**
     * Match the discovered LOQED-Bridge.
     * Input is like "LOQED-AA11BB33CC55"
     */
    private static final Pattern LOQEDPATTERN = Pattern.compile("^(LOQED-)[A-Fa-f0-9]{12}\\._http\\._tcp\\.local\\.$");

    public LoqedBridgeDiscoveryService() {
        logger.debug("Activating LOQED-Base discovery service");
    }

    @Override
    public Set<ThingTypeUID> getSupportedThingTypeUIDs() {
        return new HashSet<>(Arrays.asList(LoqedBindingConstants.THING_TYPE_BRIDGE));
    }

    @Override
    public String getServiceType() {
        return LoqedBindingConstants.SERVICE_TYPE;
    }

    @Override
    public @Nullable DiscoveryResult createResult(ServiceInfo service) {
        String name = service.getName().toLowerCase(); // Shelly Duo: Name starts with" Shelly" rather than "shelly"
        if (!name.startsWith("loqed")) {
            return null;
        }

        String address = "";
        try {
            String deviceName = LoqedBindingConstants.THING_TYPE_BRIDGE.getId();
            ThingUID thingUID = getThingUID(service);
            Map<String, Object> properties = new TreeMap<>();

            name = service.getName().toLowerCase();
            String[] hostAddresses = service.getHostAddresses();
            if ((hostAddresses != null) && (hostAddresses.length > 0)) {
                address = hostAddresses[0];
            }
            if (address.isEmpty()) {
                logger.trace("{}: LOQED device discovered with empty IP address (service-name={})", name, service);
                return null;
            }

            if (thingUID != null) {
                logger.debug("{}: Adding LOQED-Bridge {}, UID={}", name, deviceName, thingUID.getAsString());
                addProperty(properties, LoqedBindingConstants.CONFIG_DEVICEIP, address);
                addProperty(properties, LoqedBindingConstants.PROPERTY_SERVICE_NAME, name);
                addProperty(properties, LoqedBindingConstants.PROPERTY_DEV_NAME, deviceName);
                String thingLabel = deviceName + " (" + name + "@" + address + ")";
                return DiscoveryResultBuilder.create(thingUID).withProperties(properties).withLabel(thingLabel)
                        .withRepresentationProperty("deviceName").build();
            }
        } catch (NullPointerException e) {
            // maybe some format description was buggy
            logger.debug("{}: Exception on processing serviceInfo '{}'", name, service.getNiceTextString(), e);
        }
        return null;
    }

    @Override
    public @Nullable ThingUID getThingUID(ServiceInfo service) {
        Matcher matcher = LOQEDPATTERN.matcher(service.getQualifiedName());
        if (matcher.matches()) {
            return new ThingUID(LoqedBindingConstants.THING_TYPE_BRIDGE, getUIDName(service));
        } else {
            logger.debug("The discovered device is not supported, ignoring it.");
            return null;
        }
    }

    private String getUIDName(ServiceInfo service) {
        return service.getName().replaceAll("[^A-Za-z0-9_]", "_");
    }

    private void addProperty(Map<String, Object> properties, String key, @Nullable String value) {
        properties.put(key, value != null ? value : "");
    }
}
