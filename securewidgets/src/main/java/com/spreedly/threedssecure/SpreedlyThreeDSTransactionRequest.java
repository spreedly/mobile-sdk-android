package com.spreedly.threedssecure;

import android.app.Activity;

import com.seglan.threeds.sdk.AuthenticationRequestParameters;
import com.seglan.threeds.sdk.ChallengeParameters;
import com.seglan.threeds.sdk.ChallengeStatusReceiver;
import com.seglan.threeds.sdk.ThreeDS2Service;
import com.seglan.threeds.sdk.Transaction;
import com.seglan.threeds.sdk.event.CompletionEvent;
import com.seglan.threeds.sdk.event.ProtocolErrorEvent;
import com.seglan.threeds.sdk.event.RuntimeErrorEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpreedlyThreeDSTransactionRequest implements ChallengeStatusReceiver {
    ThreeDS2Service service;
    Transaction transaction;
    SpreedlyThreeDSTransactionRequestDelegate delegate;
    Activity activity;

    public SpreedlyThreeDSTransactionRequest(ThreeDS2Service service, Transaction transaction, Activity activity) {
        this.service = service;
        this.transaction = transaction;
        this.activity = activity;
    }

    public JSONArray serialize() {
        AuthenticationRequestParameters request = transaction.getAuthenticationRequestParameters();
        try {
            JSONArray wrapper = new JSONArray();

            JSONObject appId = new JSONObject();
            appId.putOpt("sdk_app_id", request.getSDKAppID());
            JSONObject encData = new JSONObject();
            encData.putOpt("sdk_enc_data", request.getDeviceData());
            JSONObject ephemPubKey = new JSONObject();
            String ephemKeyString = request.getSDKEphemeralPublicKey();
            JSONObject ephemJSON = new JSONObject(ephemKeyString);
            ephemPubKey.putOpt("sdk_ephem_pub_key", ephemJSON);
            JSONObject maxTimeout = new JSONObject();
            maxTimeout.putOpt("sdk_mac_timeout", "15");
            JSONObject referenceNumber = new JSONObject();
            referenceNumber.putOpt("sdk_reference_number", request.getSDKReferenceNumber());
            JSONObject transId = new JSONObject();
            transId.put("sdk_trans_id", request.getSDKTransactionID());

            JSONObject deviceRenderOptions = new JSONObject();
            JSONArray deviceRenderOptionsArray = new JSONArray();
            JSONObject sdkInterface = new JSONObject();
            sdkInterface.put("sdk_interface", "03");
            JSONObject sdkUiType = new JSONObject();
            sdkUiType.put("sdk_ui_type", "01");
            deviceRenderOptionsArray.put(sdkInterface);
            deviceRenderOptionsArray.put(sdkUiType);
            deviceRenderOptions.put("device_render_options", deviceRenderOptionsArray);
            wrapper.put(appId);
            wrapper.put(encData);
            wrapper.put(ephemPubKey);
            wrapper.put(maxTimeout);
            wrapper.put(referenceNumber);
            wrapper.put(transId);
            wrapper.put(deviceRenderOptions);
            return wrapper;

        } catch (JSONException exception) {
            return null;
        }
    }

    public void doChallenge(String threeDSServerTransactionID, String acsTransactionId, String acsRefNumber, String acsSignedContent) {
        ChallengeParameters parameters = new ChallengeParameters();
        parameters.set3DSServerTransactionID(threeDSServerTransactionID);
        parameters.setAcsTransactionID(acsTransactionId);
        parameters.setAcsRefNumber(acsRefNumber);
        parameters.setAcsSignedContent(acsSignedContent);
        try {
            transaction.doChallenge(activity, parameters, this, 15);
        } catch (Exception e) {
            this.delegate.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.UNKNOWN_ERROR, e));
        }
    }

    @Override
    public void completed(CompletionEvent completionEvent) {
        delegate.success(completionEvent.getTransactionStatus());
    }

    @Override
    public void cancelled() {
        delegate.cancelled();
    }

    @Override
    public void timedout() {
        delegate.timeout();
    }

    @Override
    public void protocolError(ProtocolErrorEvent protocolErrorEvent) {
        delegate.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.PROTOCOL_ERROR, protocolErrorEvent.getErrorMessage().getErrorDescription()));
    }

    @Override
    public void runtimeError(RuntimeErrorEvent runtimeErrorEvent) {
        delegate.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.RUNTIME_ERROR, runtimeErrorEvent.getErrorMessage()));
    }
}