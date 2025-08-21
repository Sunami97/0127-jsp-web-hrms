package paidLeave.service;

import paidLeave.model.PaidLeave;

public class PaidLeaveData {
    private PaidLeave paidLeave;

    public PaidLeaveData(PaidLeave paidLeave) {
        this.paidLeave = paidLeave;
    }

    public PaidLeave getPaidLeave() {
        return paidLeave;
    }
}
