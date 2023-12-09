package com.CyHawkClash.Backend.Views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class response {

    private static final SecretKeySpec secret_token = new SecretKeySpec(new byte[] {(byte) 171, (byte) 253, 24, 4, 70, 80, (byte) 197, 100, (byte) 134, 83, 41, (byte) 196, 44, 101, 105, (byte) 246, (byte) 221, 41, (byte) 242, (byte) 246, (byte) 139, (byte) 197, 53, 25, 55, 104, (byte) 137, 124, 62, (byte) 217, 88, 38}, "HmacSHA256");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final TypeReference<JSONObject> typeRef = new TypeReference<JSONObject>() {};



    public static String decode_jwt(String token) throws Exception {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setAlgorithmConstraints(new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.HMAC_SHA256));
        jws.setCompactSerialization(token);
        jws.setKey(secret_token);

        if(!(jws.verifySignature())){
            throw new Exception("Invalid Signature");
        }
        JSONObject j = mapper.readValue(jws.getPayload(), typeRef);
        System.out.println(LocalDateTime.now().isAfter(LocalDateTime.parse(j.get("exp").toString())));
        if(LocalDateTime.now().isAfter(LocalDateTime.parse(j.get("exp").toString()))){
            throw new Exception("Expired Signature");
        }
        return j.get("id").toString();
    }

    public static String encode_jwt(Map<String, Object> payload) throws Exception {
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(payload.toString().replace('=',':'));
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(secret_token);
        return jws.getCompactSerialization();
    }
}
