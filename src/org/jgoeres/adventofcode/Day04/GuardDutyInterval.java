package org.jgoeres.adventofcode.Day04;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GuardDutyInterval {
    private Date asleep;
    private Date awaken;

    public Date getAsleep() {
        return asleep;
    }

    public void setAsleep(Date asleep) {
        this.asleep = asleep;
    }

    public Date getAwaken() {
        return awaken;
    }

    public void setAwaken(Date awaken) {
        this.awaken = awaken;
    }

    public Integer getDuration(){
        long duration_ms  = getAwaken().getTime() - getAsleep().getTime();

        long duration_min = TimeUnit.MILLISECONDS.toMinutes(duration_ms);

        return (int) (long) duration_min;
    }
}
