import React, { useEffect, useState } from "react";
import sockjs from "sockjs-client/dist/sockjs";
import { CompatClient } from "@stomp/stompjs";
import Screen from "./screen";
import { useParams } from "react-router-dom";
import { timeConvert } from "../utils/util";
import jwt_decode from "jwt-decode";
import "../css/chatRoom.css";
import { getMessages } from "../apis/ChatRoomAPI";

const stompClient = new CompatClient();
stompClient.webSocketFactory = function () {
  return new sockjs("http://localhost:8080/ws");
};
stompClient.debug = () => {};

const ChatContainer = () => {
  const [contents, setContents] = useState([]);
  const [message, setMessage] = useState("");
  const [userId, setUserId] = useState("");
  const accessToken = localStorage.getItem("accessToken");
  const decodedToken = jwt_decode(accessToken);
  const params = useParams();

  // const token = localStorage.getItem("accessToken");
  // const decodedToken = jwt_decode(token);
  useEffect(() => {
    const fetchData = async () => {
      const response = await getMessages(params.chatRoomId);
      if (response.status === 200) {
        const messages = response.data;
        messages.map((msg) => {
          msg.createdAt = timeConvert(msg.createdAt);
        });
        setContents(messages);
      }
    };
    fetchData();
  }, []);
  useEffect(() => {
    if (decodedToken != null) {
      setUserId(decodedToken["sub"]);
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
          { headers: { Authorization: token } }
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
    setContents((prev) =>
      prev.concat({
        email: message.email,
        nickname: message.nickname,
        content: message.content,
        createdAt: timeConvert(message.createdAt),
      })
    );
  };
  return (
    <div className="container">
      <Screen
        contents={contents}
        handleSubmit={handleSubmit}
        message={message}
        setMessage={setMessage}
        userId={userId}
      />
    </div>
  );
};
export default ChatContainer;
