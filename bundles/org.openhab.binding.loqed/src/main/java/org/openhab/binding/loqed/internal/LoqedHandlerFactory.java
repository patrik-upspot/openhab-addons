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
package org.openhab.binding.loqed.internal;

import static org.openhab.binding.loqed.internal.constants.LoqedBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.loqed.internal.handler.LoqedBridgeHandler;
import org.openhab.core.thing.Bridge;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.ThingUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link LoqedHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Patrik Schlinker - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.loqed", service = BaseThingHandlerFactory.class)
public class LoqedHandlerFactory extends BaseThingHandlerFactory {
    private final Logger logger = LoggerFactory.getLogger(LoqedHandlerFactory.class);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        this.logger.info("TEST1");
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        this.logger.info("TEST2");
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_BRIDGE.equals(thingTypeUID)) {
            return new LoqedBridgeHandler((Bridge) thing);
        }

        return null;
    }

    @Override
    public void removeThing(ThingUID thingUID) {
        super.removeThing(thingUID);
    }

    @Override
    public void unregisterHandler(Thing thing) {
        super.unregisterHandler(thing);
    }

    @Override
    public ThingHandler registerHandler(Thing thing) {
        this.logger.info("REGISTER");
        return super.registerHandler(thing);
    }
}
