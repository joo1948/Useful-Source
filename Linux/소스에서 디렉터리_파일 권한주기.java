 // 특수문자 들어갈 수 있기 때문에 구분 + \' 추가
 String s = "chmod -R 755 \'" + fullFileNamePath +"\'";
 String[] cmd = {"/bin/sh", "-c",  s};

 Runtime.getRuntime().exec("chmod -R 755 " + outpath); //생성한 폴더의 권한 추가
 Runtime.getRuntime().exec("chmod -R 755 " + cmd);//생성한 파일의 권한 추가
