import { useState } from "react";
import { Input, InputLabel } from "@mui/material";
import { Form, useNavigate, redirect } from "react-router-dom";
import { GoogleOAuthProvider, GoogleLogin } from "@react-oauth/google";
import { signIn, oauthGoogle } from "../apis/AuthAPI";
import { useDispatch } from "react-redux";
import { SET_TOKEN } from "../Store/Auth";
import Card from "../components/ui/card";

import "../css/index.css";

const Index = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [checked, setChecked] = useState(false);

  const [email, setEmail] = useState("user1@test.com");
  const [password, setPassword] = useState("1234");

  const handleEmailInput = (e) => {
    setEmail(e.target.value);
  };
  const handlePasswordInput = (e) => {
    setPassword(e.target.value);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const response = await signIn(email, password);
    if (response.status !== 200) {
      return navigate("/");
    }
    dispatch(SET_TOKEN(response.data.accessToken));
    return navigate("/main");
  };
  const responseGoogle = async (resp) => {
    const response = await oauthGoogle(resp);
    console.log(response);
    if (response.status !== 200) {
      return navigate("/");
    }
    dispatch(SET_TOKEN(response.data.accessToken));
    return navigate("/main");
  };
  return (
    <Card>
      <form className="form" method="post" onSubmit={handleSubmit}>
        <div style={{ display: "flex", alignItems: "center" }}>
          <InputLabel className="label">E-Mail</InputLabel>
          <Input
            className="input"
            type="text"
            name="email"
            placeholder="email"
            defaultValue={email}
            onChange={handleEmailInput}
          />
        </div>
        <div style={{ display: "flex", alignItems: "center" }}>
          <InputLabel className="label">Password</InputLabel>
          <Input
            className="input"
            type="password"
            name="password"
            placeholder="password"
            defaultValue={password}
            onChange={handlePasswordInput}
          />
        </div>
        <br />
        <button className="w-btn w-btn-blue" type="submit">
          로그인
        </button>
        <button
          className="w-btn w-btn-green"
          type="button"
          onClick={() => navigate("/signup")}
        >
          회원가입
        </button>
      </form>
      <GoogleOAuthProvider clientId="522156374781-26of29k3fi4f1pij98pm8bkk1cc2trh7.apps.googleusercontent.com">
        <div className="google">
          <GoogleLogin
            buttonText="Login"
            onSuccess={responseGoogle}
            onFailure={(result) => console.log(result)}
            cookiePolicy={"single_host_origin"}
          />
        </div>
      </GoogleOAuthProvider>
    </Card>
  );
};

export default Index;
