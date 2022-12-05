import { useState } from "react";
import { Form, useNavigate, redirect } from "react-router-dom";
import { GoogleOAuthProvider, GoogleLogin } from "@react-oauth/google";
import { signIn, oauthGoogle } from "../apis/AuthAPI";
import { useDispatch } from "react-redux";
import { SET_TOKEN } from "../Store/Auth";

const Index = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [checked, setChecked] = useState(false);

  const [email, setEmail] = useState("test@gmail.com");
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
    console.log(response);
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
    <div>
      <div>
        <form
          style={{ display: "flex", flexDirection: "column" }}
          method="post"
          onSubmit={handleSubmit}
        >
          <input
            type="text"
            name="email"
            placeholder="email"
            defaultValue={email}
            onChange={handleEmailInput}
          />
          <input
            type="password"
            name="password"
            placeholder="password"
            defaultValue={password}
            onChange={handlePasswordInput}
          />
          <label>
            로그인 저장
            <input
              type="checkbox"
              checked={checked}
              onChange={() => setChecked((c) => !c)}
            />
          </label>
          <button type="submit">Login</button>
          <button type="button" onClick={() => navigate("/signup")}>
            회원가입
          </button>
        </form>
        <GoogleOAuthProvider clientId="522156374781-26of29k3fi4f1pij98pm8bkk1cc2trh7.apps.googleusercontent.com">
          <GoogleLogin
            buttonText="Login"
            onSuccess={responseGoogle}
            onFailure={(result) => console.log(result)}
            cookiePolicy={"single_host_origin"}
          />
        </GoogleOAuthProvider>
        <button>Facebook</button>
        <button>Naver</button>
      </div>
    </div>
  );
};

export default Index;
