public static String sha512(String plainText) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(plainText.getBytes());
		String hex = String.format("%0128x", new BigInteger(1, md.digest())); //암호화
		return hex;
	}

	public static String encSha512(String plainText, String plainSalt) throws Exception
	{
		String baseSalt = sha512(plainSalt);
		String newText = plainText + baseSalt.substring(baseSalt.length() - 16);
		MessageDigest md = MessageDigest.getInstance("sha-512");
		md.update(newText.getBytes());
		String result = String.format("%0128x", new BigInteger(1, md.digest()));
		return result;
	}



// ex ) String pwd = encSha512(password, “고정된salt값”);
