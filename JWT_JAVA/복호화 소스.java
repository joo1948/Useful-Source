import com.jcraft.jsch.Session;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;


public JSONObject chkTokenValid(String token) {
		JSONObject rtnObj = new JSONObject();
		
		try {
		
		SignedJWT signedJWT = (SignedJWT)SignedJWT.parse(token);
		String key = //전달해드린 Key
		JWSVerifier verifier = new MACVerifier(key);
		
		if(SignedJWT.parse(token).verify(verifier)) {
			JWTClaimsSet claimSet = signedJWT.getJWTClaimsSet();
			
			Date now = new Date();
			if(claimSet.getIssueTime().before(now) && claimSet.getExpirationTime().after(now)) {
				
				rtnObj.put("status", Return.SUCCESS);
				rtnObj.put("message", "유효한 토큰");
				
			}else {
				rtnObj.put("status", Return.FAIL);
				rtnObj.put("message", "토큰 시간 만료");
			}
		}else {
			rtnObj.put("status", Return.FAIL);
			rtnObj.put("message", "유효하지 않은 토큰");
		}
		}catch(Exception e){
			rtnObj.put("status", Return.FAIL);
			rtnObj.put("message", DicHelper.getDic("msg_apv_030"));
		}
		
		return rtnObj;
	}
