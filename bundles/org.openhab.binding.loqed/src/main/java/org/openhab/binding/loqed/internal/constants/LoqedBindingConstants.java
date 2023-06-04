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
package org.openhab.binding.loqed.internal.constants;

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link LoqedBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Patrik Schlinker - Initial contribution
 */
@NonNullByDefault
public class LoqedBindingConstants {

    private static final String BINDING_ID = "loqed";

    public static final String CONFIG_DEVICEIP = "deviceIp";
    public static final String SERVICE_TYPE = "_http._tcp.local.";
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_BRIDGE);

    public static final String PROPERTY_SERVICE_NAME = "serviceName";
    public static final String PROPERTY_DEV_NAME = "deviceName";
    public static final String PROPERTY_DEV_TYPE = "deviceType";
}
