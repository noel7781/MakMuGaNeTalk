import Card from "../components/ui/card";
import { useState } from "react";
import { Form, useNavigate } from "react-router-dom";
import { signUp, checkNicknameExist, checkEmailExist } from "../apis/AuthAPI";
import { validateEmail } from "../utils/validate";
import { isEmpty } from "../utils/util";

import "../css/signup.css";

const SignUp = () => {
  const [nickname, setNickname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [checkPassword, setCheckPassword] = useState("");
  const [errorStatus, setErrorStatus] = useState({
    nickname: true,
    email: true,
    password: true,
  });
  const navigate = useNavigate();

  const handleChangeNickname = (e) => {
    setNickname(e.target.value);
    setErrorStatus({ ...errorStatus, nickname: true });
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
    return (
      !errorStatus.nickname && !errorStatus.email && password === checkPassword
    );
  };
  const handleSubmit = async () => {
    if (checkErrorStatus(errorStatus)) {
      await signUp(nickname, email, password);
      navigate("/");
    } else {
      alert("중복 확인을 하지 않았습니다.");
    }
  };
  const checkNickname = async () => {
    if (isEmpty(nickname)) {
      alert("올바른 닉네임을 입력해주세요.");
      return;
    }
    const response = await checkNicknameExist(nickname);
    if (response.status == 200 && !response.data) {
      alert("해당 닉네임을 사용할 수 있습니다.");
      setErrorStatus({ ...errorStatus, nickname: false });
    } else {
      alert("중복된 닉네임 입니다.");
    }
  };
  const checkEmail = async () => {
    if (!validateEmail(email)) {
      alert("이메일 형식으로 입력해야 합니다.");
      return;
    }
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
    <Card>
      <h2 style={{ textAlign: "center" }}>회원가입</h2>
      <Form
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
        }}
        onSubmit={handleSubmit}
      >
        <div className="input--div">
          <label name="nickname">닉네임</label>
          <input
            type="text"
            name="nickname"
            placeholder="닉네임"
            defaultValue={nickname}
            onChange={handleChangeNickname}
          />
          <button
            className="btn check--btn"
            type="button"
            onClick={checkNickname}
          >
            중복확인
          </button>
        </div>
        <div className="input--div">
          <label name="email">이메일</label>
          <input
            type="text"
            name="email"
            placeholder="이메일"
            defaultValue={email}
            onChange={handleChangeEmail}
          />
          <button className="btn check--btn" type="button" onClick={checkEmail}>
            중복확인
          </button>
        </div>
        <div className="input--div">
          <label name="password">비밀번호</label>
          <input
            type="password"
            name="password"
            placeholder="비밀번호"
            defaultValue={password}
            onChange={handleChangePassword}
          />
          <button className="transparent" type="button" disabled>
            임시버튼
          </button>
        </div>
        <div className="input--div">
          <label name="checkPassword">비밀번호 확인</label>
          <input
            type="password"
            name="checkPassword"
            placeholder="비밀번호 확인"
            defaultValue={checkPassword}
            onChange={handleChangeCheckPassword}
          />
          <button className="transparent" type="button" disabled>
            임시버튼
          </button>
        </div>
        <div className="input--div">
          <button type="submit" className="btn submit">
            가입하기
          </button>
        </div>
      </Form>
    </Card>
  );
};

export default SignUp;
