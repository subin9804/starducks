package org.kosta.starducks.commons.notify;

import org.kosta.starducks.hr.entity.Employee;

public interface NotifyInfo {
    Employee getReceiver();
    Long getGoUrlId();
    Notify.NotificationType getNotificationType();
}
