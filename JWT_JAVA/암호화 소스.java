public void createToken(String userID, String docID) {
		try {
			
			String key = RedisDataUtil.getBaseConfig("LegacyTokenKey");
			
			Date now = new Date();
			long tokenValidMilisecond = 1000L * 60 * 3;
			Date after3minutes = new Date(now.getTime() + tokenValidMilisecond);
			
			JWSSigner signer = new MACSigner(key);
			JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
			                .claim("iat", now)
			                .claim("exp", after3minutes)
			                .claim("loginID", userID)
			                .claim("docID", docID)
			                .build();
			SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
			signedJWT.sign(signer);
			 
			String jwtString = signedJWT.serialize();
			System.out.println(jwtString);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
