package com.github.bloodriot.event.game;

import java.util.regex.Matcher;

import com.github.bloodriot.configuration.Configuration;
import com.github.bloodriot.event.Event;
import com.github.bloodriot.event.EventData;
import com.github.bloodriot.event.EventTypes;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

public class Time extends Event {
    private final Boolean bloodMoonAlerts;

    private Boolean inBloodmoon = false;
    private Boolean sentNightNotice = false;
    private Boolean sentStartNotice = false;

    private final int nightStart;
    private final int nightEnd;
    private final int warningTime;

    @Inject
    public Time(final Configuration configuration, final EventBus eventBus) {
        super(configuration, eventBus);
        setEventType(EventTypes.GAME_TIME);
        this.bloodMoonAlerts = Boolean.parseBoolean(getConfiguration().getProperty("blood-moon-alert"));
        nightStart = Integer.parseInt(getConfiguration().getProperty("night-start"));
        nightEnd = Integer.parseInt(getConfiguration().getProperty("night-end"));
        warningTime = Integer.parseInt(getConfiguration().getProperty("blood-moon-warning-time"));
    }

    @Override
    public void onEventTriggered(EventData event) {
        if (bloodMoonAlerts) {
            Matcher matcher = (Matcher) event.getData();
            int day = Integer.parseInt(matcher.group(1));
            int hour = Integer.parseInt(matcher.group(2));
            int minute = Integer.parseInt(matcher.group(3));
            hordeWarning(day, hour, minute);
        }
    }

    public EventTypes hordeWarning(final int day, final int hour, final int minute) {
        if (day % 7 == 0) {
            if (!inBloodmoon) {
                inBloodmoon = true;
                getEventBus().post(new EventData(EventTypes.HORDE_NIGHT, null));
                return EventTypes.HORDE_NIGHT;
            }
        }

        if (inBloodmoon) {
            if (!sentNightNotice && ((hour + warningTime) == nightStart)) {
                sentNightNotice = true;
                getEventBus().post(new EventData(EventTypes.HORDE_WARNING, null));
                return EventTypes.HORDE_WARNING;
            }
            if (!sentStartNotice && (hour == nightStart)) {
                sentStartNotice = true;
                getEventBus().post(new EventData(EventTypes.HORDE_START, null));
                return EventTypes.HORDE_START;
            }
            if (sentStartNotice && (hour == nightEnd)) {
                getEventBus().post(new EventData(EventTypes.HORDE_END, null));
                inBloodmoon = false;
                sentNightNotice = false;
                sentStartNotice = false;
                return EventTypes.HORDE_END;
            }
        }
        return EventTypes.NONE;
    }

    @Subscribe
    public void listen(EventData event) {
        if (event.getEventType() == getEventType()) {
            onEventTriggered(event);
        }
    }
}
