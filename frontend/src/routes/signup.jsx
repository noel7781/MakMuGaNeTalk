import { useState } from "react";
import { Form, useNavigate } from "react-router-dom";
import { signUp, checkNicknameExist, checkEmailExist } from "../apis/AuthAPI";

const SignUp = () => {
  const [nickname, setNickname] = useState("");
  const [userId, setUserId] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [checkPassword, setCheckPassword] = useState("");
  const [errorStatus, setErrorStatus] = useState({
    nickname: true,
    email: true,
  });
  const navigate = useNavigate();

  const handleChangeNickname = (e) => {
    setNickname(e.target.value);
    setErrorStatus({ ...errorStatus, nickname: true });
  };
  const handleChangeUserId = (e) => {
    setUserId(e.target.value);
  };
  const handleChangeEmail = (e) => {
    setEmail(e.target.value);
    setErrorStatus({ ...errorStatus, email: true });
  };
  const handleChangePassword = (e) => {
    setPassword(e.target.value);
  };
  const handleChangeCheckPassword = (e) => {
    setCheckPassword(e.target.value);
  };
  const checkErrorStatus = () => {
    return !errorStatus.nickname && !errorStatus.email;
  };
  const handleSubmit = async () => {
    if (checkErrorStatus(errorStatus)) {
      await signUp(nickname, userId, email, password);
      navigate("/");
    } else {
      alert("중복 확인을 하지 않았습니다.");
    }
  };
  const checkNickname = async () => {
    const response = await checkNicknameExist(nickname);
    if (response.status == 200 && !response.data) {
      alert("해당 닉네임을 사용할 수 있습니다.");
      setErrorStatus({ ...errorStatus, nickname: false });
    } else {
      alert("중복된 닉네임 입니다.");
    }
  };
  const checkEmail = async () => {
    const response = await checkEmailExist(email);
    if (response.status == 200) {
      if (!response.data) {
        alert("해당 이메일을 사용할 수 있습니다.");
        setErrorStatus({ ...errorStatus, email: false });
      } else {
        alert("중복된 이메일입니다.");
      }
    } else {
      alert("올바른 이메일 형식이 아닙니다.");
    }
  };

  return (
    <div>
      <h2>회원가입</h2>
      <Form
        style={{ display: "flex", flexDirection: "column" }}
        onSubmit={handleSubmit}
      >
        <div>
          <label name="nickname">닉네임</label>
          <input
            type="text"
            name="nickname"
            placeholder="닉네임"
            defaultValue={nickname}
            onChange={handleChangeNickname}
          />
          <button type="button" onClick={checkNickname}>
            중복확인
          </button>
        </div>
        <div>
          <label name="email">이메일</label>
          <input
            type="text"
            name="email"
            placeholder="이메일"
            defaultValue={email}
            onChange={handleChangeEmail}
          />
          <button type="button" onClick={checkEmail}>
            중복확인
          </button>
        </div>
        <div>
          <label name="password">비밀번호</label>
          <input
            type="password"
            name="password"
            placeholder="비밀번호"
            defaultValue={password}
            onChange={handleChangePassword}
          />
        </div>
        <div>
          <label name="checkPassword">비밀번호 확인</label>
          <input
            type="password"
            name="checkPassword"
            placeholder="비밀번호 확인"
            defaultValue={checkPassword}
            onChange={handleChangeCheckPassword}
          />
        </div>
        <button type="submit" style={{ border: "2px solid blue" }}>
          가입하기
        </button>
      </Form>
    </div>
  );
};

export default SignUp;
