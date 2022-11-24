import React, { useEffect, useState } from "react";
import sockjs from "sockjs-client/dist/sockjs";
import { CompatClient } from "@stomp/stompjs";
import Screen from "./screen";
import { useParams } from "react-router-dom";
import { useJwt } from "react-jwt";

import "../css/chatRoom.css";

const token = localStorage.getItem("accessToken");
const stompClient = new CompatClient();
stompClient.webSocketFactory = function () {
  return new sockjs("http://localhost:8080/ws");
};
const ChatContainer = () => {
  const [contents, setContents] = useState([]);
  const [message, setMessage] = useState("");
  const [userInfo, setUserInfo] = useState({});
  const params = useParams();
  const { decodedToken, isExpired } = useJwt(token);

  useEffect(() => {
    if (decodedToken != null) {
      console.log(decodedToken);
      setUserInfo(decodedToken["sub"]);
    }
  }, [decodedToken]);

  useEffect(() => {
    let token = localStorage.getItem("accessToken");
    stompClient.connect(
      { Authorization: token },
      () => {
        stompClient.subscribe(
          `/topic/room.${params.chatRoomId}`,
          (data) => {
            const newMessage = JSON.parse(data.body);
            addMessage(newMessage);
          },
          { Authorization: token }
        );
      },
      (e) => {
        console.error(e);
      }
    );
  }, [contents]);

  const handleSubmit = (e) => {
    if (message.trim().length === 0) {
      return;
    }
    let token = localStorage.getItem("accessToken");
    const newMessage = {
      content: message,
    };
    stompClient.publish({
      destination: `/app/chat.message.${params.chatRoomId}`,
      headers: { Authorization: token },
      body: JSON.stringify(newMessage),
    });
    setMessage("");
    e.preventDefault();
  };
  const addMessage = (message) => {
    setContents((prev) => [
      ...prev,
      {
        content: message.content,
      },
    ]);
  };
  return (
    <div className="container">
      <Screen
        contents={contents}
        handleSubmit={handleSubmit}
        message={message}
        setMessage={setMessage}
        username={userInfo}
      />
    </div>
  );
};
export default ChatContainer;
