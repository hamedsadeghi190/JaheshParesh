package ir.yeksaco.jahesh.webService.iterfaces;

import ir.yeksaco.jahesh.common.enums.FailType;

public interface iwebServicelistener{
    void OnSuccess(Object response);
    void OnFailed(FailType type, String message);
}
