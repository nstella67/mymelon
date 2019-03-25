<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>media/readMP3.jsp</title>
  <style type="text/css">
  *{
    font-family: gulim;
    font-size: 20px;
  }
  </style>
  <link href="../css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class='title'>음악듣기</div>

  <div class='content'>
  	${dto.title }<br>
  	<img src="./storage/${dto.poster }" width="400"><br>
  	<audio  src="./storage/${dto.filename }"
  	        controls autoplay></audio>
  </div>

  <div class='bottom'>
    <input type='button' value='음원목록'
           onclick="location.href='./list.do?mediagroupno=${dto.mediagroupno}'">
  </div>
</body>
</html>
