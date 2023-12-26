package org.kosta.starducks.commons.notify;

import org.kosta.starducks.hr.entity.Employee;

import java.util.List;

public interface NotifyInfo {
    List<Employee> getReceivers();
    String getGoUrl();
    Notify.NotificationType getNotificationType();
}
