<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <style>
    table {
      width: 100%;
    }

    th {
      background: #eee;
      padding: 5px;
    }

    td {
      text-align: center;
      padding: 5px;
    }

    th,
    td {
      border: 1px solid #eee;
    }

    a {
      padding: 10px 30px;
      display: inline-block;
      margin: 5px;
      background: #99aaaa;
      color: #fff;
      border-radius: 5px;
      text-decoration: none;
      font-weight: bold;
    }
  </style>
</head>

<body>
  <a href="/student/input_student">학생데이터 입력하기</a>

  <table>
    <tr>
      <th>
        idx
      </th>
      <th>
        이름
      </th>
      <th>
        주소
      </th>
      <th>
        생일
      </th>
    </tr>
    ${select_result}
  </table>
</body>

</html>
