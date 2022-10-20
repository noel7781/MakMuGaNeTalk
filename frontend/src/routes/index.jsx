import { useState } from "react";
import { Form } from "react-router-dom";

const Index = () => {
  const [checked, setChecked] = useState(false);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleEmailInput = (e) => {
    setEmail(e.target.value);
  };
  const handlePasswordInput = (e) => {
    setPassword(e.target.value);
  };

  return (
    <div>
      <div>
        <Form
          style={{ display: "flex", flexDirection: "column" }}
          method="post"
          action="/signin"
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
          <button type="submit" style={{ border: "2px solid blue" }}>
            Login
          </button>
          <button type="button" style={{ border: "2px solid blue" }}>
            회원가입
          </button>
        </Form>
        <button>Google</button>
        <button>Facebook</button>
        <button>Naver</button>
      </div>
    </div>
  );
};

export default Index;