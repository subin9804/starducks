package org.kosta.starducks.commons.notify;

import org.kosta.starducks.commons.BaseTimeEntity;
import org.kosta.starducks.hr.entity.Employee;

public class DocumentRequest extends BaseTimeEntity implements NotifyInfo {
    @Override
    public Employee getReceiver() {
        return null;
    }

    @Override
    public Long getGoUrlId() {
        return null;
    }

    @Override
    public Notify.NotificationType getNotificationType() {
        return Notify.NotificationType.DOCUMENT;
    }
}
