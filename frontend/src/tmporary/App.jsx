import { useState } from "react";
import "./App.css";
import ChatRoom from "./ChatRoom";

const App = () => {
  const [cid, setCid] = useState("1");
  const [username, setUsername] = useState("");
  const [connect, setConnect] = useState(false);
  const [loginStatus, setLoginStatus] = useState(false);
  const [userId, setUserId] = useState("");
  const [userPassword, setUserPassword] = useState("");
  return (
    <div>
      <label>채팅방 번호</label>
      <input
        type="text"
        value={cid}
        onChange={(e) => setTitle(e.target.value)}
      />
      <br />
      <label>사용자 이름</label>
      <input
        type="text"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <br />
      <button style={{ border: "2px solid" }} onClick={(e) => setConnect(true)}>
        생성
      </button>
      {connect ? <ChatRoom cid={cid} username={username} /> : <></>}
      {/* <ChatRoom title={title} username={username} /> */}
    </div>
  );
};

export default App;
