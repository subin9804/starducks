package org.kosta.starducks.commons.notify.service;

import org.kosta.starducks.commons.notify.entity.Notify;
import org.kosta.starducks.hr.entity.Employee;

import java.util.List;

public interface NotifyInfo {
    List<Employee> getReceivers();
    String getGoUrl();

    String getMsg();
    Notify.NotificationType getNotificationType();
}
