package com.spreedly.threedssecure;

import android.app.Activity;
import android.util.Base64;

import com.seglan.threeds.sdk.AuthenticationRequestParameters;
import com.seglan.threeds.sdk.ChallengeParameters;
import com.seglan.threeds.sdk.ChallengeStatusReceiver;
import com.seglan.threeds.sdk.ThreeDS2Service;
import com.seglan.threeds.sdk.Transaction;
import com.seglan.threeds.sdk.event.CompletionEvent;
import com.seglan.threeds.sdk.event.ProtocolErrorEvent;
import com.seglan.threeds.sdk.event.RuntimeErrorEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpreedlyThreeDSTransactionRequest {

    @NonNull
    ThreeDS2Service service;
    @NonNull
    Transaction transaction;
    @NonNull
    Activity activity;

    public SpreedlyThreeDSTransactionRequest(@NonNull ThreeDS2Service service, @NonNull Transaction transaction, @NonNull Activity activity) {
        this.service = service;
        this.transaction = transaction;
        this.activity = activity;
    }

    @Nullable
    public String serialize() {
        AuthenticationRequestParameters request = transaction.getAuthenticationRequestParameters();
        try {
            JSONObject wrapper = new JSONObject();

            wrapper.putOpt("sdk_app_id", request.getSDKAppID());
            wrapper.putOpt("sdk_enc_data", request.getDeviceData());
            String ephemKeyString = request.getSDKEphemeralPublicKey();
            JSONObject ephemJSON = new JSONObject(ephemKeyString);
            wrapper.putOpt("sdk_ephem_pub_key", ephemJSON);
            wrapper.putOpt("sdk_max_timeout", "15");
            wrapper.putOpt("sdk_reference_number", request.getSDKReferenceNumber());
            wrapper.put("sdk_trans_id", request.getSDKTransactionID());
            JSONObject deviceRenderOptions = new JSONObject();
            deviceRenderOptions.put("sdk_interface", "03");
            deviceRenderOptions.put("sdk_ui_type", "01");
            wrapper.put("device_render_options", deviceRenderOptions);
            return Base64.encodeToString(wrapper.toString().getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);

        } catch (JSONException exception) {
            return null;
        }
    }

    public void doChallenge(@NonNull JSONObject scaAccess, @NonNull SpreedlyThreeDSTransactionRequestListener listener) {
        ChallengeParameters parameters = new ChallengeParameters();
        try {
            parameters.set3DSServerTransactionID(scaAccess.getString("xid"));
            parameters.setAcsTransactionID(scaAccess.getString("acs_transaction_id"));
            parameters.setAcsRefNumber(scaAccess.getString("acs_reference_number"));
            parameters.setAcsSignedContent(scaAccess.getString("acs_signed_content"));
        } catch (Exception e) {
            listener.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.INVALID_INPUT, "Bad sca_authentication JSON"));
        }
        try {
            transaction.doChallenge(activity, parameters, new ListenerToChallengeStatusReceiver(listener), 15);
        } catch (Exception e) {
            listener.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.UNKNOWN_ERROR, e));
        }
    }

    public void doChallenge(@NonNull String scaAccess, @NonNull SpreedlyThreeDSTransactionRequestListener listener) {
        try {
            doChallenge(new JSONObject(Objects.requireNonNull(scaAccess)), listener);
        } catch (JSONException exception) {
            listener.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.UNKNOWN_ERROR, "Bad sca_authentication JSON"));
        }
    }

    class ListenerToChallengeStatusReceiver implements  ChallengeStatusReceiver {
        private final SpreedlyThreeDSTransactionRequestListener listener;

        public ListenerToChallengeStatusReceiver(SpreedlyThreeDSTransactionRequestListener listener) {
            this.listener = listener;
        }

        @Override
        public void completed(@NonNull CompletionEvent completionEvent) {
            if (listener != null) {
                listener.success(completionEvent.getTransactionStatus());
            }
        }

        @Override
        public void cancelled() {
            if (listener != null) {
                listener.cancelled();
            }
        }

        @Override
        public void timedout() {
            if (listener != null) {
                listener.timeout();
            }
        }

        @Override
        public void protocolError(@NonNull ProtocolErrorEvent protocolErrorEvent) {
            if (listener != null) {
                listener.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.PROTOCOL_ERROR, protocolErrorEvent.getErrorMessage().getErrorDescription()));
            }
        }

        @Override
        public void runtimeError(@NonNull RuntimeErrorEvent runtimeErrorEvent) {
            if (listener != null) {
                listener.error(new SpreedlyThreeDSError(SpreedlyThreeDSError.SpreedlyThreeDSErrorType.RUNTIME_ERROR, runtimeErrorEvent.getErrorMessage()));
            }
        }
    }
}